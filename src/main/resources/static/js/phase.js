//exit z bloku registrácii/editácii
let hiddenBlock = document.getElementById("behind-scene");
let overlay = document.getElementById("overlay");
let exit= document.getElementById("exit");
exit.addEventListener("click",exitF);
//

//pridať závod Listener
let addPhase = document.getElementById("addPhase");
addPhase.addEventListener("click",showAddPhase);
//
function showAddPhase(){
    hiddenBlock.classList.add("active");
    overlay.classList.add("active");
}
function exitF() {
    hiddenBlock.classList.remove("active");
    overlay.classList.remove("active");
}
//bloky na registráciu/editáciu
let phaseBlock = document.getElementById("phaseBlock");
let settingsBlock = document.getElementById("settingsBlock");

//Listeners na zmenu blokov medzi sebou
let phaseClick = document.getElementById("first");
let settingsClick = document.getElementById("second");

phaseClick.addEventListener('click',changeToPhaseBlock);
settingsClick.addEventListener('click',changeToSettings);

function changeToSettings() {
    phaseBlock.style.display='none';
    settingsBlock.style.display='block';
}
function changeToPhaseBlock() {
    settingsBlock.style.display="none";
    phaseBlock.style.display='block';
}

