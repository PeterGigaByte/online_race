console.log("gay")
// filter na mená pretekov
let searchBox = document.getElementById("searchRace");
searchBox.addEventListener('keydown',filter);
//

//filter podľa dátumu
let dateFromF = document.getElementById("from");
let dateToF = document.getElementById("to");
dateFromF.addEventListener("change",filterDate);    //TODO
dateToF.addEventListener("change",filterDate);      //TODO
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

//editovať závod Listener
let editActiveRace = document.getElementById("editActiveRace");
editActiveRace.addEventListener("click",showAddRace);
//

//nastavenia automatické generovanie dráh
let tracksNumber = document.getElementById("tracksNumber");
let tracks = document.getElementById("tracks").children;
tracksNumber.addEventListener('keyup',tracksCheck);
tracks.item(0).classList.remove("hidden");

//checkbox Hala Prepojenie
let checkboxH = document.getElementById("checkboxH");
let checkboxH2 = document.getElementById("checkboxH2");
let checkboxR = document.getElementById("checkboxR");
let checkboxO = document.getElementById("checkboxO");
checkboxH.addEventListener("change",sync);
checkboxH2.addEventListener("change",sync2);
checkboxR.addEventListener("change",f1);
checkboxO.addEventListener("change",f2);

f(checkboxH);f(checkboxH2);f(checkboxO);f(checkboxR);

//generovanie

//
let eight=[6,4,2,1,3,5,7,8,9,10];
function tracksCheck() {
    for(let index = 0;index<tracks.length;index++){
        if(index<tracksNumber.value){
            tracks.item(index).classList.remove("hidden");
        }else {
            tracks.item(index).classList.add("hidden");
        }
        if(tracksNumber.value==4){
            tracks.item(index).children[0].children.item(0).value=index+1;
        }
        else{
            tracks.item(index).children[0].children.item(0).value=eight[index];
        }
    }
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
//todo
function filterDate() {
    let from = dateFromF.value.split("-");
    let to = dateToF.value.split("-");
    let dateFrom=new Date(from[2]+'-'+from[1]+'-'+from[0]);
    let dateTo=new Date(to[2]+'-'+to[1]+'-'+to[0]);
    if (!from && !to) { // no value for from and to
        return;
    }
    let  table, tr, td, i;
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");
    for (i = 1; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("th")[0];
        let dateFromTable = td.innerText.split(".");
        let dateTable = new Date(dateFromTable[2]+'-'+dateFromTable[1]+'-'+dateFromTable[0]);
        console.log(dateTo);
        if (td) {
            if (dateFrom.getTime()<dateTable.getTime() && dateTable.getTime()<dateTo.getTime() &&  dateFrom.getTime()<dateTo.getTime()) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function sync() {
    syncAll(checkboxH,checkboxH2);


}
function sync2() {
    syncAll(checkboxH2, checkboxH);

}
function syncAll(x,y) {
    x.value = !!x.checked;
    y.value=x.value;
    y.checked=x.checked;
    f(x);f(y);
    if(x.checked){
        tracksNumber.value=4;
        tracksCheck();
    }
    else{
        tracksNumber.value=8;
        tracksCheck();
    }
}
function f(checkboxT) {
    if (checkboxT.checked){
        checkboxT.value=0;
    }else{
        checkboxT.value=1;
    }
}
function f1() {
    f(checkboxR);
}
function f2() {
    f(checkboxO);
}