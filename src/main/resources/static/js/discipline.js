//exit z bloku registrácii/editácii
let hiddenBlock = document.getElementById("behind-scene");
let overlay = document.getElementById("overlay");
let exit= document.getElementById("exit");
exit.addEventListener("click",exitF);
//

//pridať závod Listener
let addDiscipline = document.getElementById("addDiscipline");
addDiscipline.addEventListener("click",showAddDiscipline);
//
function showAddDiscipline(){
    hiddenBlock.classList.add("active");
    overlay.classList.add("active");
}
function exitF() {
    hiddenBlock.classList.remove("active");
    overlay.classList.remove("active");
}
let disciplines = ["40 m","50 m","60 m"];
let categories = ["Muži","Ženy","Juniori","Juniorky"];
let disciplineInput = document.getElementById("disciplineName");
let categoryInput = document.getElementById("categoryName");

autocomplete(disciplineInput, disciplines);
autocomplete(categoryInput, categories);

