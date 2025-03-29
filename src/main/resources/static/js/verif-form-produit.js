var form = document.querySelector('form')
console.log(form)
var seuil = document.querySelector('input[name="seuil"]')
var message_err_seuil = document.querySelector('#seuilmessage')

var selectInputs = document.querySelectorAll('select[name="typeProduit"]')

var checkcouleursInputs = document.querySelectorAll('input[type="checkbox"][name="couleurs"]')
var checktaillesInputs = document.querySelectorAll('input[type="checkbox"][name="tailles"]')
var count_couleurs = 0
var count_tailles = 0
var isValid = true

function valider_form() {
    var bon = true
    selectInputs.forEach((select) => {
        if (select.selectedIndex === 0) {
            bon = false
        }
    })

    checkcouleursInputs.forEach((checkbox) => {
        if (checkbox.checked) {
            count_couleurs++;
        }
    })
    checktaillesInputs.forEach((checkbox) => {
        if (checkbox.checked) {
            count_tailles++;
        }
    })

    console.log(count_couleurs);
    console.log(count_tailles);

    if (count_couleurs < 1) {
        bon = false
    } else {
        bon = true
    }
    if (count_tailles < 1) {
        bon = false
    }else {
        bon = true
    }

    console.log(bon);
    count_couleurs = 0
    count_tailles = 0
    return bon
}
seuil.addEventListener('input', () => {
    let quantite = document.querySelector('input[name="quantite"]')
    var val_seuil = parseInt(seuil.value)
    var val_quant = parseInt(quantite.value)
    if (isNaN(val_quant)) {
        val_quant = 0
    }
    if (val_seuil > val_quant) {
        message_err_seuil.classList.add('error-show');
        message_err_seuil.classList.remove('error-hide')
        isValid = false
    }
    else if (val_seuil <= val_quant){
        message_err_seuil.classList.remove('error-show')
        message_err_seuil.classList.add('error-hide')
        isValid = true
    }
})

form.addEventListener('submit', (event) => {
    if (!isValid && !valider_form()) {
        event.preventDefault();
        alert("Veillez respecter les exignces !")
    }
    valider_form()
    console.log(isValid);
    console.log("ppop");
})