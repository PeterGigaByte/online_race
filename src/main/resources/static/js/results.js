$(document).ready(function() {
    let bibNumbers = new Map();
    let selectedDisciplines;
    let disciplineTables = $(".display");
    let table = $('#tableLeft').DataTable({
        "searching": false,
        "ordering": false,
        "paging":   false,
        "info":     false,
        select:         true
    });
    if($.cookie('selectedDisciplines')!=null){
        selectedDisciplines=$.parseJSON($.cookie("selectedDisciplines"))
        $(".display").addClass("hidden");
        for(let i = 0; i < selectedDisciplines.length; i++){
            $('#'+selectedDisciplines[i]).removeClass("hidden");
            table.row(+table.row($('#'+selectedDisciplines[i]+'aside')).index()).select();
        }
    }
    table.on( 'select', function ( e, dt, type, indexes ) {
        selectedDisciplines = $.map(table.rows('.selected').data(), function (item) {
            return item[0];
        });
        $(".display").addClass("hidden");
        for(let i = 0; i < selectedDisciplines.length; i++){
            $('#'+selectedDisciplines[i]).removeClass("hidden");

        }
        $.cookie("selectedDisciplines", JSON.stringify(selectedDisciplines));

        // do something with the ID of the selected items
    } );


    const createdCell = function(cell) {
        let original;
        cell.setAttribute('contenteditable', true);
        cell.setAttribute('spellcheck', false);
        cell.addEventListener("focus", function(e) {
            original = e.target.textContent
        });
        cell.addEventListener("blur", function(e) {
            if (original !== e.target.textContent) {
                const row = table.row(e.target.parentElement);
                row.invalidate();
            }
        })
    };
    var nameType = $.fn.dataTable.absoluteOrder( {
        value: '', position: 'bottom'
    } );
    $(".rightTable").DataTable({
        "searching": false,
        "paging":   false,
        "bInfo": false,
        "bPaginate": false,
        "info":  false,
        select:  false,
        "ordering": true,
        "autoWidth": false,
        "columnDefs" : [{
            'bSortable': false,
            'aTargets': ['nosort']
        },{"targets":4, "type":"date-eu"},{ targets: 0, type: nameType },{ targets: 6, type: nameType },{ "targets": 6, createdCell: createdCell},{ "targets": 7, createdCell: createdCell}],

    });


    $(".saveAthletes").click(function () {
        let saveTable = $(this).closest('table');
        let tbody = saveTable.find("tbody > tr > td");
        if(tbody.length == 1){
            console.log("empty");
            return;
        }
        let disciplineId = this.attributes.item(0).value;
        let tableForm = saveTable.tableToJSON({
            headings:{0:"Poradie",1:"Line",2:"Bib",3:"Meno",4:"D??tum narodenia",5:"Klub",6:"V??kon atl??ta",7:"Reak??n?? ??as"},
            extractor : {
                0 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                1 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                2 : function(cellIndex, $cell) {
                    return  $cell.text();
                },
                3 : function(cellIndex, $cell) {
                    return $cell.attr('value');
                },
                4 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                5 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                6 : function(cellIndex, $cell) {
                    return $cell.text();
                },
                7 : function(cellIndex, $cell) {
                    return $cell.text();
                },
            }
        });
        tableForm.splice(0,1);tableForm.splice(0,1);
        let discipline = {};
        discipline ["id"] = disciplineId;
        tableForm.push(discipline);
        console.log(tableForm);
        ajaxEditApplications(tableForm)
    });
    function ajaxEditApplications(tableForm) {
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/edit/results",
            data : JSON.stringify(tableForm),
            dataType: 'text',
            success: function(result){
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'green',
                    content: 'V??sledky boli ??spe??ne editovan??.',
                    delayOnHover: true,
                    showCountdown: true
                });
                setTimeout(function () {
                    location.reload();
                }, 500);
            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Nepodarilo sa upravi?? v??sledky !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e);
            }
        });
    }
    $("#importCsv").click(function () {
        importCsv();
    });
    function importCsv() {
        $.ajax({
            type : "GET",
            url : window.location + "/importResultsFromCsv",
            async:false,
            success: function(result){
                if(result=="success"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'green',
                        content: 'V??sledky boli ??spe??ne importovan??.',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                }

                else{
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohu??ial, nepodarilo sa importova?? v??sledky !!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }

            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Bohu??ial, nepodarilo sa importova?? v??sledky !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)

            }
        });
    }
    $("#setAbsolutOrder").click(function () {
        setAbsoluteOrder();
    });
    function setAbsoluteOrder() {
        $.ajax({
            type : "PUT",
            url : window.location + "/absoluteOrder",
            async:false,
            success: function(result){
                if(result=="success"){
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'green',
                        content: 'Absol??tne poradie bolo ur??en??.',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                }

                else{
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohu??ial, nepodarilo sa ur??i?? absol??tne poradie !!',
                        delayOnHover: true,
                        showCountdown: true
                    });
                }

            },
            error : function(e) {
                new jBox('Notice', {
                    animation: 'flip',
                    color: 'red',
                    content: 'Bohu??ial, nepodarilo sa ur??i?? absol??tne poradie !!',
                    delayOnHover: true,
                    showCountdown: true
                });
                console.log(e)

            }
        });
    }



} );

