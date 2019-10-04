$(document).ready(getButtons);

function getButtons() {
    $.ajax({
        url: "rest/gallery/tags",
        dataType: "json",
        success: function (data) {
            var tags = data;
            var html = "";
            for (var i = 0; i < tags.length; i++) {
                html += "<button class='tagButton' onclick='getThumbnails(\"" + htmlEscape(tags[i]) + "\")'>" + htmlEscape(tags[i]) + "</button>";
            }
            $(".buttons").html(html);

        }
    })
}

function getThumbnails(tag) {
    $.ajax({
        url: "rest/gallery/tags/" + tag,
        dataType: "json",
        success: function (data) {
            var photos = data;
            var html = "";
            for (var i = 0; i < photos.length; i++) {
                if (photos[i].name != null) {
                    html += "<img alt='thumbnail' class='thumbnail' src=\"thumbnail?name=" +
                        htmlEscape(photos[i].name) + "\" onclick='getImageInfo(\"" +
                        htmlEscape(photos[i].name) + "\")'>";
                }
            }
            $(".images").html(html);
            $(".imageInfo").html("");

        }
    })
}
 
function getImageInfo(name) {
    $.ajax({
        url: "rest/gallery/" + name,
        dataType: "json",
        success: function (data) {
            var slika = data;
            if (slika != null) {
                var html = "<img alt='fullImage' class='fullImg' src=\"fullImage?name=" + htmlEscape(slika.name) + "\">";
                html += "<h3>" + htmlEscape(slika.info) + "</h3>";
                var tags = slika.tags;
                for (var i = 0; i < tags.length; i++) {
                    var c="";
                    if(i<(tags.length-1)){
                        c = ",";
                    }
                    html += "<span class='tag'> " + htmlEscape(tags[i]) + c +" </span>";
                }
                $(".imageInfo").html(html);
            }

        }
    })

}
