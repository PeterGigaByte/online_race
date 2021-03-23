//exit z bloku registrácii/editácii
let hiddenBlock = document.getElementById("behind-scene");
let overlay = document.getElementById("overlay");
let exit= document.getElementById("exit");
exit.addEventListener("click",exitF);
//

//pridať závod Listener
let addClub = document.getElementById("addClub");
addClub.addEventListener("click",showAddClub);
//
function showAddClub(){
    hiddenBlock.classList.add("active");
    overlay.classList.add("active");
}
function exitF() {
    hiddenBlock.classList.remove("active");
    overlay.classList.remove("active");
}



let preview = document.getElementById("preview");
let image = document.getElementById("image");
image.addEventListener("change",changeImage);
function changeImage() {
    let file = image.files[0];
    let reader = new FileReader();
    reader.onload = function(e){
        preview.src =e.target.result;
    };
    reader.readAsDataURL(file);

}
$(document).ready(function() {
    $('#table').DataTable({
            "columnDefs": [ {
                "targets": [5 ,6],
                "orderable": false
            } ]
        }
    );
} );
