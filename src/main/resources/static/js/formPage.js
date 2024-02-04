$(document).ready(function() {
	$(".dbList tbody tr").hover(function() {
		$(this).addClass("active-row")
	}, function() {
		$(this).removeClass("active-row")
	})
	
	$(".container").css("height",$('#sideNav').height());
	
	$(".listHeaderContainer thead tr").children().each(function(index, val) {
		$(val).width($($(".dbListData thead tr").children()[index]).width())
	})
})