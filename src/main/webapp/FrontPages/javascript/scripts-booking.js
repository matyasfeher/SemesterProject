$(function() {

    var i=1;
	$("#add_row").click(function(){
		$('#addr'+i).html("<td><input name='name"+i+"' type='text' placeholder='First Name' class='form-control input-sm'  /> </td><td><input  name='mail"+i+"' type='text' placeholder='Last Name'  class='form-control input-sm'></td>");

		$('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
		i++; 
	});

	$("#delete_row").click(function(){
		if(i>1){
			$("#addr"+(i-1)).html('');
			i--;
		}
	});

	$("#bookNow a").click(function(){
		$('#successModal').modal();
	});

	$("#seeError").click(function(){
		$('#successModal').modal('hide');
		$('#errorModal').modal();
	});

});