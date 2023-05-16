function
regMelding() {
    const melding = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    };
    if(
        validerPassord() &&
        validerBrukernavn()){
        $.post("/lagre", melding, function(){
            hentAlle();
        });
    }
}
function
validerBrukernavn(){
    const brukernavn = $("#brukernavn").val();
    const regexp = /^[a-zæøåA-ZÆØÅ0-9 .\-]{2,30}$/;
    const ok = regexp.test(brukernavn);
    if(!ok){
        $("#feilBrukernavn").html("Brukernavnet må mellom 2 og 30 bokstaver og tall (bindestrek og mellomrom er også tillatt)");
        return false;
    }
    else{
        $("#feilBrukernavn").html("");
        return true;
    }
}
function
validerPassord(){
    const passord = $("#passord").val();
    const regexp = /^[a-zøæåA-ZÆØÅ0-9]{6,10}$/;
    const ok = regexp.test(passord);
    if(!ok){
        $("#feilPassord").html("Passordet må være mellom 6 og 10 bokstaver og tall");
        return false;
    }
    else{
        $("#feilPassord").html("");
        return true;
    }
}

$(function(){
// kjøres når dokumentet er ferdig lastet
    hentAlle();
});
function
hentAlle() {
    $.get( "/hentAlleBrukernavn", function( brukernavn ) {
        formaterData(brukernavn);
    });
}
function
formaterData(brukernavn) {
    let ut = "<table class='table table-striped'><tr><th>Brukernavn</th></tr>";
    for (const bruker of brukernavn) {
        ut += "<tr><td>" + bruker +"</td></tr>";
    }
    ut += "</table>";
    $("#listing").html(ut);
}

function
login() {
    const melding = {
        brukernavn : $("#brukernavn").val(),
        passord : $("#passord").val()
    };
    $.get( "/logginn", melding, function( OK ) {
        if(OK){
            $("#statusLoggetInn").html("Du er logget inn!");
        }
        else{
            $("#statusLoggetInn").html("Du er ikke logget inn!");
        }
    });
}