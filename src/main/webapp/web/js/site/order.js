(function(){
	function submit(){}
	
	function toggleNewAddrSection(flag){
		flag ? $('.new-addr').removeClass('hide') : $('.new-addr').addClass('hide');
	}
	
	$('#new-addr-btn').bind('click', function(){
		toggleNewAddrSection(true);
	});
}());