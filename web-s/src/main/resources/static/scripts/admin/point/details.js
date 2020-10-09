$(function(){
	var defaultVal = parseInt($('#pointVariationDefault').val(),10);
	var min = parseInt($('#pointVariationMin').val(),10);
	var max = parseInt($('#pointVariationMax').val(),10);
	var size = parseInt($('#pointVariationSize').val(),10);
	
	$('#pointVariation').val(defaultVal);
	
	$(".fa-plus-square").click(function(){
		
		var pointVariation = parseInt($('#pointVariation').val(),10);
		
		if(pointVariation + size <= max){
			$('#pointVariation').val(pointVariation + size);
		}
		
	});
	
	$(".fa-minus-square").click(function(){
		
		var pointVariation = parseInt($('#pointVariation').val(),10);
		
		if(pointVariation - size >= min){
			$('#pointVariation').val(pointVariation - size);
		}
		
	});

	
	
});