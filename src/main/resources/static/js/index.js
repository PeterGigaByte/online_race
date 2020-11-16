let searchBox = document.getElementById("searchRace");
searchBox.addEventListener('keydown',filter);

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