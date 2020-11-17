// filter na mená pretekov
let searchBox = document.getElementById("searchRace");
searchBox.addEventListener('keydown',filter);
//

//bloky na registráciu/editáciu
let registrationBlock = document.getElementById("registrationBlock");
let detailsBlock = document.getElementById("detailsBlock");
let settingsBlock = document.getElementById("settingsBlock");
//

//exit z bloku registrácii/editácii
let hiddenBlock = document.getElementById("behind-scene");
let overlay = document.getElementById("overlay");
let exit= document.getElementById("exit");
exit.addEventListener("click",exitF);
//

//Listeners na zmenu blokov medzi sebou
let registrationClick = document.getElementById("first");
let detailsClick = document.getElementById("second");
let settingsClick = document.getElementById("third");
detailsClick.addEventListener('click',changeToDetails);
registrationClick.addEventListener('click',changeToRegistration);
settingsClick.addEventListener('click',changeToSettings);
//

//pridať závod Listener
let addRace = document.getElementById("addRace");
addRace.addEventListener("click",showAddRace);
//

//nastavenia automatické generovanie dráh
let tracksNumber = document.getElementById("tracksNumber");
let tracks = document.getElementById("tracks");
tracksNumber.addEventListener('change',generateTracks);
//







function generateTracks() {
    let i = tracksNumber.value;
    let tracksString='';
    let track = 1;
    for (let index = 0; index<i;index++){
        tracksString=tracksString+"<div th:class=\"input-div\">\n" +
            "                                            <label>\n" +
                                                            track+".\n" +
            "                                                <input th:class=\"input-box\" th:type=\"select\">\n" +
            "                                            </label>\n" +
            "                                        </div>";
        track++;
    }
    tracks.innerHTML=tracksString;

}
function showAddRace() {
    hiddenBlock.classList.add("active");
    overlay.classList.add("active");
}
function exitF() {
    hiddenBlock.classList.remove("active");
    overlay.classList.remove("active");
}
function changeToSettings() {
    detailsBlock.style.display="none";
    registrationBlock.style.display='none';
    settingsBlock.style.display='block';
}
function changeToRegistration() {
    detailsBlock.style.display="none";
    settingsBlock.style.display="none";
    registrationBlock.style.display='block';
}
function changeToDetails() {
    registrationBlock.style.display='none';
    settingsBlock.style.display="none";
    detailsBlock.style.display="block";

}
function filter() {
    let input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchRace");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("th")[1];
        console.log(td[i]);
        if (td) {

            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}