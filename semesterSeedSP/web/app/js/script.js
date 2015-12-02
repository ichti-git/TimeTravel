//Panel close button
$(function(){
$('.clickable').on('click',function(){
    var effect = $(this).data('effect');
        $(this).closest('.panel')[effect]();
	})
})

//Show/hide panel button 
$('#searchResultsPanel').hide();
$('#searchButton').click(function(){
	$('#searchResultsPanel').toggle();
});

// One page Smooth Scrolling
$('a[href*=#]:not([href=#])').click(function() {
    if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
        var target = $(this.hash);
        target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
        if (target.length) {
            $('html,body').animate({
                scrollTop: target.offset().top
            }, 1000);
            return false;
        }
    }
});

//Google maps display//
$(document).ready(function(){
    function initialize() {
      var mapProp = {
        center:new google.maps.LatLng(55.680987, 12.579357),
        zoom:14,
        mapTypeId:google.maps.MapTypeId.ROADMAP
      };
      var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
    }

    if(typeof google != 'undefined') google.maps.event.addDomListener(window, 'load', initialize);
});

$('#searchDate').datepicker({
     orientation: 'auto',
	 format: 'dd.mm.yyyy'
});				
	
// Modal window
$(function() {
            $('#reservationButton').on('click', function(e) {
                e.preventDefault();
                $('#reservationWizard').bPopup(
				{
					position: ['auto','auto']
				});
            });
        });

//Wizard form		
$(document).ready(function () {

    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            allNextBtn = $('.nextBtn');

    allWells.hide();

    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
                $item = $(this);

        if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-primary').addClass('btn-default');
            $item.addClass('btn-primary');
            allWells.hide();
            $target.show();
            $target.find('input:eq(0)').focus();
        }
    });

    allNextBtn.click(function(){
        var curStep = $(this).closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url']"),
            isValid = true;

        $(".form-group").removeClass("has-error");
        for(var i=0; i<curInputs.length; i++){
            if (!curInputs[i].validity.valid){
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }

        if (isValid)
            nextStepWizard.removeAttr('disabled').trigger('click');
    });

    $('div.setup-panel div a.btn-primary').trigger('click');
});		

//Move to result detail
$('#accordion').on('shown.bs.collapse', function () {
  var panel = $(this).find('.in'); 
  $('html, body').animate({
        scrollTop: panel.offset().top -50
  }, 500);
});