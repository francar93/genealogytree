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
if(x.length){
    $('.search-nologin').click(function(){
        $('.header').addClass('logged');
        $('.search-logged').show();
    });
}

$(document).ready(function() {
  var myProfile = $(".container-left");
  var posizione = myProfile.position();

  $(window).scroll(function() {
    if ($(window).scrollTop() >= posizione.top) {
      myProfile.addClass("fixed");
    } else {
      myProfile.removeClass("fixed"); 
    }
  });
});
