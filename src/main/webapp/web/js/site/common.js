(function(){
	var Root = location.protocol + '//' + location.host + '/client/',
	App = {
			gotoLogin : function(){
				location.href = Root + 'web/html/login.html';
			},
			goto404 : function(){
				location.href = Root + 'web/html/error/404.html';
			},
			goto500 : function(){
				$.tipBox({message: '订水服务未启动，请稍后再试', type: 'error'});
			}
	},
	Ajax = {
			successHandler : function(success, fail){
				success = success || foo;
				fail = fail || foo;
				return function(data){
					Ajax.removeMask();
					if (data.success) {
						success(data);
					} else {
						fail(data);
					}
				};
			},
			errorHandler : function(error){
				error = error || foo;
				return function(jqXHR, tStatus, errorThrown){
					Ajax.removeMask();
					error.apply(null, arguments);
					if(jqXHR.status == '401') {
						App.gotoLogin();
					} else if (jqXHR.status == '404') {
						App.goto404();
					} else if (jqXHR.status >=  500) {
						App.goto500();
					}
				};
			},
			getURL : function(url){
				url = $.trim(url);
				if ('/' == url[0]) {
					url = url.substr(1);
				}
				return Root + url;
			},
			addMask : function(){
				$('body').addClass('loading');
			},
			removeMask : function(){
				$('body').removeClass('loading');
			}
	},
	foo = function(){},
	getURLParams = function(){
		var url = location.href,
		data = {};
		var query = url.split('?')[1];
		if (query) {
			var params = query.split('&');
			for (var i = 0, len = params.length; i < len; i++) {
				var kvs = params[i].split('=');
				data[kvs[0]] = kvs[1];  
			}
		}
		return data;
	},
	defaults = {
			type : 'POST',
			data : {},
			dateType : 'json',
			success : foo,
			fail : foo,
			error : foo,
			always : foo
	},
	token = getURLParams().token;
	window.Ajax = {
			json : function(opts){
				var ajaxOpts = $.extend({}, defaults, opts, {
					contentType : 'application/json; charset=UTF-8',
					dataType : 'json',
					url : Ajax.getURL(opts.url),
					data : opts.data ? 
							($.isPlainObject(opts.data) ? JSON.stringify($.extend({token: token}, opts.data)) : opts.data) 
								: JSON.stringify({token: token}) 
				});
				ajaxOpts.success = Ajax.successHandler(opts.success, opts.fail);
				ajaxOpts.error = Ajax.errorHandler(opts.error);
//				Ajax.addMask();
				return $.ajax(ajaxOpts);
			},
			postJSON : function(opts){
				opts = opts || {};
				opts.type = 'POST';
				return Ajax.json(opts);
			},
			getJSON : function(opts){
				opts = opts || {};
				opts.type = 'GET';
				return Ajax.json(opts);
			}
	};
	$.tipBox = function(opts){
		opts = $.extend({
			type : 'default',
			message : '',
			autoDel : true,
			time: 1500
		}, opts);
		var $div = $('<div>').addClass('slide-in-up tip-box-wrap ' + opts.type).appendTo(document.body);
		$('<div>').addClass('tip-box-con').html(opts.message).appendTo($div);
		if (opts.autoDel) {
			setTimeout(function(){
				$div.addClass('slide-out-up');
				setTimeout(function(){
					$div.remove();
				}, 500);
			}, opts.time);
		} else {
			// add close btn
			var $closeBtn = $('<a>').addClass('');
		}
	};
	// mark error
	$('input, select').on('blur', function(){
		var $ipt = $(this); 
		if ($ipt.hasClass('required') && !$ipt.val()) {
			$ipt.addClass('error');
		} else {
			$ipt.removeClass('error');
		}
	});
	// common methods
	window.app = {
		getURLParams : getURLParams,
		gotoLogin : App.gotoLogin
	};
}());