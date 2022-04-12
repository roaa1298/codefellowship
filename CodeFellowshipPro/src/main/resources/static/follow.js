 function change()
{
    var elem = document.getElementById("unfollow");
//    btn.addEventListener('click', () => {
//      btn.style.display = 'none';
//    });
    if (elem.value=="follow"){
    elem.value = "unfollow";
    elem.name="unfollow";
    }
    else {
    elem.value = "follow";
    elem.name="follow";
    }
}