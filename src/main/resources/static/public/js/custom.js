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

        $('#profile-picture').on('change', function(event) {
        	var image = $('.user-avatar-image');
        	image.attr('src', URL.createObjectURL(event.target.files[0]))
        });

});


