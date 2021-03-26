var jBoxLogin = {
    jBox: null,

    // The html of each of the content containers

    html: {
        discipline:
            '<div id="LoginContainer-discipline" class="login-container">' +
            '   <div class="login-body">' +
            '       <input type="text" id="id" class="login-textfield hidden" >' +
            '       <input style="width: 100px; display: inline-block; float: left" type="time" id="disciplineTime" class="login-textfield " placeholder="Čas štartu" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <select style="width: 300px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="disciplineName" class="login-textfield select-css" placeholder="Disciplína" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <select style="width: 200px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="category" class="login-textfield select-css" placeholder="Kategória" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <select style="width: 130px; display: inline-block; float: left; margin-left: 20px; margin-top:26px" type="text" id="phaseName" class="login-textfield select-css" placeholder="Fáza" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required></select>' +
            '       <input style="width: 80px; display: inline-block; float: left; margin-left: 20px" type="number" id="phaseNumber" class="login-textfield" placeholder="Poradie" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" required>' +
            '       <input style="display: inline-block;  margin-top: 20px" type="text" id="note" class="login-textfield" placeholder="Poznámka" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <button id="createDiscipline" style="width: 200px;" type="submit" class="login-button">Vytvoriť</button>' + '       <button id="createAndClose" name="createAndClose" style="width: 200px; display: inline-block; float: right; margin-left: 20px" type="submit" class="login-button">Vytvoriť a zavrieť</button>' +
            '   </div>' +
            '   <div class="login-footer">' +
            '       <span id="settings-footer" onclick="jBoxLogin.jBox.showContent(\'settings\')">Nastavenia</span>' +
            '       <br>' +
            '   </div>' +
            '</div>',

        settings:
            '<div id="LoginContainer-settings" class="login-container">' +
            '           <div class="login-body">' +
            '       <input style="width: 100px; display: inline-block; float: left" type="number" id="Q" class="login-textfield " placeholder="Q" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <input style="width: 100px; display: inline-block; float: left; margin-left: 20px; margin-top:26px" type="number"  id="q" class="login-textfield" placeholder="q" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" >' +
            '       <select style="width: 200px; display: inline-block; float: left; margin-left: 20px; margin-top:26px"   id="aim" class="login-textfield select-css" placeholder="Kategória" autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false" ></select>' +
            '           </div>' +
            '       <div class="login-footer">' +
            '           <span onclick="jBoxLogin.jBox.showContent(\'discipline\')">Disciplína</span>' +
            '           <br>' +
            '       </div>' +
            '       </div>',

    },

    // Corresponding titles for content elements

    title: {
        discipline: 'Disciplína',
        settings: 'Nastavenia',
    },

    // These tooltips will show when a textelemet gets focus

    textfieldTooltips: {
        disciplineTime: 'Čas štartu',
        disciplineName: 'Názov disciplíny',
        category: 'Kategória',
        phaseName: 'Názov fázy',
        phaseNumber: 'Číslo fázy',
        note: 'Poznámka',
        Q:"Q - Podľa umiestnenia v behu",
        q:"q - Podľa umiestnenia celkovo podľa času",
        aim: "Cieľ postupu"

    }
};
$(document).ready(function () {
    let dayWeek = ["Nedeľa" , "Pondelok","Utorok","Streda","Štvrtok","Piatok","Sobota"];
    let disciplines_list_run =["40 m","50 m","60 m"];
    let disciplines_list_jump =["Skok do diaľky",];
    let disciplines_list_throw =["Hod kladivom"];
    let categories_list =["Muži","Ženy","Dorastenci","Dorastenky"];
    let phases_list =["Rozbeh","Finále"];
    let datesSelect = $("#day");
    let disciplinesSelect = $("#disciplinesType");
    let categoriesSelect = $("#categories");

    refreshDates();
    refreshDisciplines();
    refreshCategories();



    function refreshDates(){
        $.ajax({
            type : "GET",
            url : window.location + "/dates",
            success: function(result){
                datesSelect.empty();
                $.each(result, function(i, date){
                    date = new Date(date);
                    let month = date.getMonth()+1;
                    datesSelect.append('<option value="'+date.getFullYear()+'-'+month+'-'+date.getDate()+'">'+date.getDate()+'.('+dayWeek[date.getDay()]+')'+month+'.'+date.getFullYear()+'</option>')
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshDisciplines(){
        $.ajax({
            type : "GET",
            url : window.location + "/disciplineTypes",
            success: function(result){
                disciplinesSelect.empty();
                $.each(result, function(i, discipline){
                    disciplinesSelect.append('<option value="'+discipline+'">'+discipline+'</option>');
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshCategories(){
        $.ajax({
            type : "GET",
            url : window.location + "/categories",
            success: function(result){
                categoriesSelect.empty();
                $.each(result, function(i, category){
                    categoriesSelect.append('<option value="'+category+'">'+category+'</option>');
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function refreshAim(){
        let discipline = $("#disciplineName").val();
        discipline = discipline.split("_");
        let data = {
            id:$("#id").val(),
            disciplineName:discipline[1],
            disciplineCategory:$("#category").val()
        };
        $.ajax({
            type : "POST",
            contentType : "application/json",
            accept: 'text/plain',
            url : window.location + "/disciplines",
            data : JSON.stringify(data),
            dataType: 'json',
            success: function(result){
                aimSelect.empty();
                aimSelect.append('<option value="'+null+'">'+"Nevybrané"+'</option>');
                $.each(result, function(i, aim){
                    aimSelect.append('<option value="'+aim.id+'">'+aim.disciplineTime+' - '+aim.phaseName+' - '+aim.phaseNumber+'</option>');
                });
            },
            error : function(e) {
                console.log(e)
            }
        });
    }
    function setDisciplineSelect() {
        disciplineNameSelect.empty();
        $.each(disciplines_list_run,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="run_'+discipline_name+'" >'+discipline_name+'</option>');
        });
        $.each(disciplines_list_jump,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="jump_'+discipline_name+'" >'+discipline_name+'</option>');
        });
        $.each(disciplines_list_throw,function (i,discipline_name) {
            disciplineNameSelect.append('<option  value="throw_'+discipline_name+'" >'+discipline_name+'</option>');
        });

    }
    function setCategoriesSelect() {
        categoriesListSelect.empty();
        $.each(categories_list,function (i,category_name) {
            categoriesListSelect.append('<option value="'+category_name+'" >'+category_name+'</option>');
        })
    }
    function refreshPhases(){
        phasesSelect.empty();
        $.each(phases_list,function (i,phase_name) {
            phasesSelect.append('<option value="'+phase_name+'" >'+phase_name+'</option>');
        })
    }
    jBoxLogin.jBox = new jBox('Modal', {

        // Unique id for CSS access
        id: 'jBoxLogin',

        // Dimensions
        width: 940,
        height: 300,

        // Attach to elements
        attach: '#addDiscipline',

        // Create the content with the html provided in global var
        content: '<form id="discipline"><div id="LoginWrapper">' + jBoxLogin.html.discipline  + jBoxLogin.html.settings +'</div></form>',

        // Adjust header when scroll is blocked
        blockScrollAdjust: ['header'],

        // When the jBox is being initialized add internal functions
        onInit: function () {

            // Internal function to show content
            this.showContent = function (id, force) {

                // Abort if an ajax call is loading
                if (!force && $('#LoginWrapper').hasClass('request-running')) return null;

                // Set the title depending on id
                this.setTitle(jBoxLogin.title[id]);

                // Show content depending on id
                $('.login-container.active').removeClass('active');
                $('#LoginContainer-' + id).addClass('active');

                // Remove error tooltips
                $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                    $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
                });

            };

            // Initially show content for login
            this.showContent('discipline', true);

            // Add focus and blur events to textfields
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {

                // Focus an textelement
                $('#' + id).on('focus', function () {

                    // When there is an error tooltip close it
                    $(this).data('jBoxTextfieldError') && $(this).data('jBoxTextfieldError').close();

                    // Remove the error state from the textfield
                    $(this).removeClass('textfield-error');

                    // Store the tooltip jBox in the elements data
                    if (!$(this).data('jBoxTextfieldTooltip')) {
                        $(this).data('jBoxTextfieldTooltip', new jBox('Tooltip', {
                            width: 310,
                            theme: 'TooltipSmall',
                            addClass: 'LoginTooltipSmall',
                            target: $(this),
                            position: {
                                x: 'left',
                                y: 'top'
                            },
                            outside: 'y',
                            offset: {
                                y: 6,
                                x: 8
                            },
                            pointer: 'left:17',
                            content: tt,
                            animation: 'move'
                        }));
                    }

                    $(this).data('jBoxTextfieldTooltip').open();

                    // Loose focus of textelement
                }).on('blur', function () {
                    $(this).data('jBoxTextfieldTooltip').close();
                });
            });

            // Internal function to show errors
            this.showError = function (element, message) {

                if (!element.data('errorTooltip')) {
                    element.data('errorTooltip', new jBox('Tooltip', {
                        width: 310,
                        theme: 'TooltipError',
                        addClass: 'LoginTooltipError',
                        target: element,
                        position: {
                            x: 'left',
                            y: 'top'
                        },
                        outside: 'y',
                        offset: {
                            y: 6
                        },
                        pointer: 'left:9',
                        content: message,
                        animation: 'move'
                    }));
                }

                element.data('errorTooltip').open();
            };

            // Internal function to change checkbox state
            this.toggleCheckbox = function () {
                // Abort if an ajax call is loading
                if ($('#LoginWrapper').hasClass('request-running')) return null;

                $('.login-checkbox').toggleClass('login-checkbox-active');
            };

            // Add checkbox events to checkbox and label
            $('.login-checkbox, .login-checkbox-label').on('click', function () {
                this.toggleCheckbox();
            }.bind(this));

            // Parse an ajax repsonse
            this.parseResponse = function (response) {
                try {
                    response = JSON.parse(response.responseText || response);
                } catch (e) {}
                return response;
            };

            // Show a global error
            this.globalError = function () {
                new jBox('Notice', {
                    color: 'red',
                    content: 'Oops, something went wrong.',
                    attributes: {
                        x: 'right',
                        y: 'bottom'
                    }
                });
            };

            // Internal function to disable or enable the form while request is running
            this.startRequest = function () {
                this.toggleRequest();
            }.bind(this);

            this.completeRequest = function () {
                this.toggleRequest(true);
            }.bind(this);

            this.toggleRequest = function (enable) {
                $('#LoginWrapper')[enable ? 'removeClass' : 'addClass']('request-running');
                $('#LoginWrapper button')[enable ? 'removeClass' : 'addClass']('loading-bar');
                $('#LoginWrapper input, #LoginWrapper button').attr('disabled', enable ? false : 'disabled');
            }.bind(this);
            // Bind ajax login function to login button
        },
        onOpen: function () {
            // Go back to login when we open the modal
            this.showContent('discipline', true);
        },
        onClose: function () {
            // Remove error tooltips
            $.each(jBoxLogin.textfieldTooltips, function (id, tt) {
                $('#' + id).data('jBoxTextfieldError') && $('#' + id).data('jBoxTextfieldError').close();
            });
        }
    });
    let typeSubmit;
    $("#createDiscipline").click(function () {
        typeSubmit = "createDiscipline";
    });
    $("#createAndClose").click(function () {
        typeSubmit = "createAndClose";
    });
    $("#discipline").submit(function (event) {
       event.preventDefault();
       let disciplineNameAndType = $("#disciplineName").val();
       disciplineNameAndType = disciplineNameAndType.split("_");
        console.log(disciplineNameAndType[0]);
        console.log(disciplineNameAndType[1]);
       let formDiscipline = {
           id: $("#id").val(),
           disciplineTime:$("#disciplineTime").val(),
           date:$("#day").val(),
           disciplineName:disciplineNameAndType[1],
           disciplineType:disciplineNameAndType[0],
           category:$("#category").val(),
           phaseName:$("#phaseName").val(),
           phaseNumber:$("#phaseNumber").val(),
           note:$("#note").val(),
           Q:$("#Q").val(),
           q:$("#q").val(),
           aim:$("#aim").val(),
       };
        ajaxSaveDiscipline();

        function ajaxSaveDiscipline() {
            $.ajax({
                type : "POST",
                contentType : "application/json",
                accept: 'text/plain',
                url : window.location + "/save",
                data : JSON.stringify(formDiscipline),
                dataType: 'text',
                success : function(result) {
                    if(result == "Already exist"){
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'red',
                            content: 'Bohužial, Atletickú disciplínu sa nepodarilo vytvoriť, pretože rovnaká už existuje !!',
                            delayOnHover: true,
                            showCountdown: true
                        });
                    }else{
                        clearForm();
                        new jBox('Notice', {
                            animation: 'flip',
                            color: 'green',
                            content: 'Atletická disciplína bola úspešne vytvorená',
                            delayOnHover: true,
                            showCountdown: true
                        });
                    }
                    if(typeSubmit == "createDiscipline"){


                    }else if( typeSubmit == "createAndClose"){

                        jBoxLogin.jBox.close();
                    }else if( typeSubmit == "edit"){

                        jBoxLogin.jBox.close();
                    }


                    refreshCategories();
                    refreshDisciplines();
                    refreshPhases();
                    refreshAim()
                },
                error : function(e) {
                    new jBox('Notice', {
                        animation: 'flip',
                        color: 'red',
                        content: 'Bohužial, Atletickú disciplínu sa nepodarilo vytvoriť',
                        delayOnHover: true,
                        showCountdown: true
                    });
                    console.log("ERROR: ", e);

                },
            });
        }
    });

    function clearForm(){
        $("#id").val("");
        $("#disciplineTime").val("");
        $("#disciplineName").val("");
        $("#category").val("");
        $("#phaseName").val("");
        $("#phaseNumber").val("");
        $("#note").val("");
        $("#Q").val("");
        $("#q").val("");
        $("#aim").val("");
        setDisciplineSelect();
        setCategoriesSelect();
    }
    let categoriesListSelect = $("#category");
    let disciplineNameSelect = $("#disciplineName");
    let phasesSelect = $("#phaseName");
    let aimSelect = $("#aim");
    setDisciplineSelect();
    setCategoriesSelect();
    refreshPhases();
    refreshAim();


    $("#settings-footer").change(function () {
        refreshAim()
    });
});
/*
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

$(document).ready(function() {
    $('#table').DataTable({
            "columnDefs": [ {
                "targets": [5 ,6, 7 ],
                "orderable": false
            } ]
        }
    );
} );
*/
