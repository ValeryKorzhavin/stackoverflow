$(document).ready(function () {
   var token = $("meta[name='_csrf']").attr("content");
   var header = $("meta[name='_csrf_header']").attr("content");

    $('.question').find('.vote-up').on('click', function(event) {
        event.preventDefault();
        var question = $(this).parents('.question');
        var id = question.attr('data-question-id');
        var rating = question.find('.question_rating');

        var xhr = new XMLHttpRequest();
        xhr.open('PATCH', '/questions/' + id + '/like', true);
        xhr.setRequestHeader(header, token);
        xhr.send();
        xhr.onreadystatechange = function() {
            if (this.readyState != 4) return;
            if (xhr.status != 200) {
                alert(xhr.status + ': ' + xhr.statusText);
            } else {
                rating.text(JSON.parse(xhr.responseText).rating);
            }
        }
    });
    $('.question').find('.vote-down').on('click', function(event) {
        event.preventDefault();
        var question = $(this).parents('.question');
        var id = question.attr('data-question-id');
        var rating = question.find('.question_rating');

        var xhr = new XMLHttpRequest();
        xhr.open('PATCH', '/questions/' + id + '/dislike', true);
        xhr.setRequestHeader(header, token);
        xhr.send();
        xhr.onreadystatechange = function() {
            if (this.readyState != 4) return;
            if (xhr.status != 200) {
                alert(xhr.status + ': ' + xhr.statusText);
            } else {
                 rating.text(JSON.parse(xhr.responseText).rating);
            }
        }
    });

    $('.answer').find('.vote-up').on('click', function(event) {
            event.preventDefault();
            var answer = $(this).parents('.answer');
            var id = answer.attr('data-answer-id');
            var rating = answer.find('.answer_rating');

            var xhr = new XMLHttpRequest();
            xhr.open('PATCH', '/answers/' + id + '/like', true);
            xhr.setRequestHeader(header, token);
            xhr.send();
            xhr.onreadystatechange = function() {
                if (this.readyState != 4) return;
                if (xhr.status != 200) {
                    alert(xhr.status + ': ' + xhr.statusText);
                } else {
                    rating.text(JSON.parse(xhr.responseText).rating);
                }
            }
        });
        $('.answer').find('.vote-down').on('click', function(event) {
            event.preventDefault();
            var answer = $(this).parents('.answer');
            var id = answer.attr('data-answer-id');
            var rating = answer.find('.answer_rating');

            var xhr = new XMLHttpRequest();
            xhr.open('PATCH', '/answers/' + id + '/dislike', true);
            xhr.setRequestHeader(header, token);
            xhr.send();
            xhr.onreadystatechange = function() {
                if (this.readyState != 4) return;
                if (xhr.status != 200) {
                    alert(xhr.status + ': ' + xhr.statusText);
                } else {
                     rating.text(JSON.parse(xhr.responseText).rating);
                }
            }
        });

        $('form').submit(function(event) {
            $(this).find("input[type='submit']").prop('disabled', true).addClass('disabled');
        });

//        $('#question_filter_form').submit(function(event) {
//            event.preventDefault();
//            var filters = $(this).find('input:checkbox:checked').map(function() {
//                return $(this).val();
//            }).get().join(',');
//              see URLSearchParams
//            $('#question_filter_form').find('input[name=filters]').val(filters);
//            window.location.href =
//        });

        var search = function(pattern) {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/accounts', true);
            xhr.setRequestHeader(header, token);
            xhr.send();
        };

        var timeout = null;

        $('.account-search').find('input#search').on('keyup', function(event) {
            var that = this;
            if (timeout != null) {
                clearTimeout(timeout);
            }
            timeout = setTimeout(function() {
                search(); 
                console.log($(that).val());
            }, 500);
        });

//        $('.edit-question').on('click', function() {
//            var body = $(this).parents('.content').find('.question-body');
//            var contentText = body.text();
//            var question = $(this);
//            var id = question.attr('data-question-id');
//
//            body.replaceWith('<><>');
//
//            var xhr = new XMLHttpRequest();
//            xhr.open('GET', '/accounts', true);
//            xhr.setRequestHeader(header, token);
//            xhr.send();
//
//            console.log('click edit');
//        });



//        $('.edit-question').on('click', function() {
//            var question = $(this);
//            var id = question.attr('data-question-id');
//
//            var parent = $(this).parents('.content');
//        	var text = $(this).parents('.content').find('.question-body').text();
//
//        	var before = $("<div></div>").addClass('question-body');
//            var editForm = $("<form action='#' class='edit-form'><div class='form-group'>"+
//                "<textarea class='form-control edit-area' cols='30' " +
//                "rows='5' name='text'>"+text+"</textarea></div><button type='submit' "+
//                "class='save-changes btn btn-primary btn-sm mr-1'>save</button>"+
//                "<button type='button' class='btn-sm cancel-edit btn btn-secondary'>cancel</button></form>");
//
//        	$(this).parents('.content').find('.question-body').replaceWith(editForm);
//
//            editForm.on('submit', function(event) {
//                event.preventDefault();
//                var formData = new FormData(event.target);
//                var changedBody = formData.get('text');
//                var xhr = new XMLHttpRequest();
//                xhr.open('PATCH', '/questions/' + id, true);
//                xhr.setRequestHeader(header, token);
//                xhr.setRequestHeader("Content-Type", "application/json")
//                xhr.send(JSON.stringify({body: changedBody}));
//
//                xhr.onreadystatechange = function() {
//                    if (this.readyState != 4) return;
//                    if (xhr.status != 200) {
//                    } else {
//                        var modified = JSON.parse(xhr.responseText).body;
//                        parent.find('.edit-form').replaceWith(before.text(modified));
//                    }
//                }
//            });
//
//        	parent.on('click', '.cancel-edit', function(event) {
//                parent.find('.edit-form').replaceWith(before.text(text));
//                console.log('cancel edit');
//                event.stopPropagation();
//            });
//        });
//
//        $('.edit-answer').on('click', function() {
//
//            var answer = $(this);
//            var id = answer.attr('data-answer-id');
//
//            var parent = $(this).parents('.answer-content');
//            var text = $(this).parents('.answer-content').find('.answer-body').text();
//            console.log(text + '???');
//
//            var before = $("<div></div>").addClass('answer-body');
//
//            var answerEditForm = $("<form action='#' class='answer-edit-form'><div class='form-group'>"+
//                "<textarea class='form-control edit-area' cols='30' " +
//                "rows='5' name='text'>"+text+"</textarea></div><button type='submit' "+
//                "class='answer-save-changes btn btn-primary btn-sm mr-1'>save</button>"+
//                "<button type='button' class='btn-sm answer-cancel-edit btn btn-secondary'>cancel</button></form>");
//
//            $(this).parents('.answer-content').find('.answer-body').replaceWith(answerEditForm);
//
//            parent.on('click', '.answer-cancel-edit', function(event) {
//                parent.find('.answer-edit-form').replaceWith(before.text(text));
//                console.log('cancel edit');
//                event.stopPropagation();
//            });
//
//            answerEditForm.on('submit', function(event) {
//                event.preventDefault();
//                var formData = new FormData(event.target);
//                var changedBody = formData.get('text');
//                var xhr = new XMLHttpRequest();
//                xhr.open('PATCH', '/answers/' + id, true);
//                xhr.setRequestHeader(header, token);
//                xhr.setRequestHeader("Content-Type", "application/json")
//                xhr.send(JSON.stringify({body: changedBody}));
//
//                xhr.onreadystatechange = function() {
//                    if (this.readyState != 4) return;
//                    if (xhr.status != 200) {
//                    } else {
//                        modified = JSON.parse(xhr.responseText).body;
//                        parent.find('.answer-edit-form').replaceWith(before.text(modified));
//                    }
//                }
//            });
//
//        });


});


