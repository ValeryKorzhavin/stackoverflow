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
});


