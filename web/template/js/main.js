/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
    Created on : 8-mag-2016, 22.22.02
    Author     : Lorenzo
*/
var x = $('#btn-name-logged');
if(x.length){ //guardo se sono loggato
    $('.search-nologin').click(function(){
        $('#search').attr("disabled", "true"); //disattivo la ricerca nolog
        $('.header').addClass('logged'); //setto alcune proprietà css per il box
        $('.search-logged').show(); //mostro il tutto sopra il profilo
    });
}
$('.dropdown-my').click(function(){ 
    $('.header').removeClass('logged'); //setto alcune proprietà css per il box
    $('.search-logged').hide(); //nascondo il box ricerca loggata
});

//al click su genitori chiudo tutti i div dei genitori connessi
$('#genitori').click(function(){
    $('.genitori').toggleClass('not-visible');
    $('#genitori').toggleClass('radius');
});
//al click su partner chiudo il div del partner connesso
$('#partner').click(function(){
    $('.partner').toggleClass('not-visible');
    $('#partner').toggleClass('radius');
});
//al click su sons chiudo tutti i div dei figli connessi
$('#figli').click(function(){
    $('.figli').toggleClass('not-visible');
    $('#figli').toggleClass('radius');
});
//al click su brthers and sisters chiudo tutti i div dei fratelli connessi
$('#fratelli').click(function(){
    $('.fratelli').toggleClass('not-visible');
    $('#fratelli').toggleClass('radius');
});