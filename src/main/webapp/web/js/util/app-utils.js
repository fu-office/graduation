(function(window, $) {
	window.Desktop = {};
	window.token = "";
	window.SYSURL = {
		ADDR : 'map/getAllProvince'
	};
	function getDesktop(win) {
		if (win.des == 'desktop') {
			window.Desktop = win.Desktop;
			window.ROOT = win.ROOT;
			window.token = win.token;
		} else {
			if (win == win.parent) {
				return;
			} else {
				getDesktop(win.parent);
			}
		}
	}
	getDesktop(window.parent);
	$(window).focus(function() {
		window.parent.isFocus = true;
	});

	var baseStaticFunction = {
		extend : function(protoProps) {
			var child;

			if (protoProps && protoProps.hasOwnProperty('constructor')) {
				child = protoProps.constructor;
			} else {
				child = function() {
					View.apply(this, arguments);
				};
			}

			$.extend(child.prototype, protoProps || {});

			child.prototype.constructor = child;

			return child;
		}
	};

	window.View = function(pro) {
		this.prototype = {};
	};
	// add static function
	$.extend(View, baseStaticFunction, {
		baseStaticFunction : baseStaticFunction
	});
	
	// $.ajaxSetup({
	// 	cache : false
	// });
	
	/**
	 * 将Form数据转化为JSON对象
	 */
	var F2J = {
		FORM_PATTERN : /.+\[\d*\]+$/,
		generate : function(obj, name, value) {
			if (obj[name] === undefined) {
				obj[name] = value;
			} else {
				if (!obj[name].push) {
					obj[name] = [ obj[name] ];
				}
				obj[name].push(value);
			}
			return obj;
		},
		translate : function(arrs) {
			var obj = {};
			$.each(arrs, function() {
				if (this.name.indexOf('.') != -1) {
					var params = this.name.split('.'), //
					innerobj = obj;
					for ( var i = 0; i < params.length - 1; i++) {
						var index = -1, //
						name = params[i], //
						tobj;
						if (F2J.FORM_PATTERN.test(name)) {
							index = name.substring(name.indexOf('[') + 1, name.indexOf(']'));
							name = name.substring(0, name.indexOf('['));
						}
						tobj = innerobj[name];
						// 未定义，则初始化
						if (tobj === undefined) {
							if (-1 === index) {
								innerobj[name] = {};
							} else {
								innerobj[name] = [];
							}
						}
						if (-1 === index) {
							innerobj = innerobj[name];
						} else {
							if (!innerobj[name][index]) {
								innerobj[name][index] = {};
							}
							innerobj = innerobj[name][index];
						}
					}
					F2J.generate(innerobj, params[params.length - 1], this.value);
				} else {
					F2J.generate(obj, this.name, this.value);
				}
			});
			return obj;
		}
	};
	$.fn.f2j = function(opts) {
		var $this = $(this), //
		$els = $this.find('input[name]:enabled, select[name]:enabled, textarea[name]:enabled'), //
		arrs = [];
		opts = {
			trim : true
		};

		while ($els.length > 0) {
			var $el = $els.eq(0);
			if ($el.is('input[type=text]') || $el.is('input[type=hidden]') || $el.is('textarea')) {
				arrs.push({
					name : $el.attr('name'),
					value : $.trim($el.val())
				});
				$els = $els.not($el);
			} else if ($el.is('input[type=checkbox]')) {
				var name = $el.attr('name'), //
				$checkbox = $els.filter('[name="' + name + '"]'), //
				$checked = $checkbox.filter(':checked');
				vals = [];
				for ( var i = 0; i < $checked.length; i++) {
					vals.push($checked.eq(i).val());
				}
				arrs.push({
					name : name,
					value : vals
				});
				$els = $els.not($checkbox);
			} else if ($el.is('input[type=radio]')) {
				var name = $el.attr('name'), //
				$radio = $els.filter('[name="' + name + '"]');
				arrs.push({
					name : name,
					value : $radio.filter(':checked').val()
				});
				$els = $els.not($radio);
			} else if ($el.is('select')) {
				arrs.push({
					name : $el.attr('name'),
					value : $el.val()
				});
				$els = $els.not($el);
			} else {
				console.log('解析错误', $els);
				$els = $els.not($el);
			}
		}
		return F2J.translate(arrs);
	};

	//将json数据填充至调用容器
	var J2F = {
		parse : function parse($els, data, prefix) {
			var prefix = prefix || "", dataItem, $el;
			for ( var n in data) {
				if (!data.hasOwnProperty(n) || (dataItem = data[n]) == undefined)
					continue;
				$el = $els.filter("[name='" + prefix + n + "']");
				if ($el.length > 0 && !$.isPlainObject(dataItem)) {
					if ($el.is("input[type='text']") || $el.is("input[type='hidden']") || $el.is("textarea")) {
						$el.val(dataItem);
					} else if ($el.is("input[type='radio']")) {
						$el.filter("[value='" + dataItem + "']").prop("checked", true);
					} else if ($el.is("input[type='checkbox']")) {
						if ($.isArray(dataItem)) {
							$.each(dataItem, function() {
								$el.filter("[value='" + this + "']").prop("checked", true);
							});
						} else {
							$el.prop("checked", dataItem);
						}
					} else if ($el.is("select")) {
						$el.val(dataItem);
						$el.select ? $el.select("value", dataItem) : $el.find("[value='" + dataItem + "']").prop("checked");
					} else if ($el.not("input,textarea,select")) {
						typeof dataItem === "string" && !/<(?!script)\w+\s+[^\n\f\r]*>/.test(dataItem) && (dataItem = dataItem.replace(/</g,"&lt;").replace(/>/g,"&gt;"));
						$el.html(dataItem);
					}
					$els = $els.not($el);
				} else {
					if ($.isPlainObject(dataItem)) {
						parse($els, dataItem, prefix + n + ".");
					}
				}
			}
		}
	};

	$.fn.j2f = function(data) {
		var $this = $(this),
			$els = $this.find("[name]");
		if(typeof data === "string"){
			data = JSON.parse(data);
		}
		J2F.parse($els,data);
		return $this;
	};

	{
		$.fn.mask = function(options) {
			options = options == undefined ? true : options;
			var $this = $(this);
			if (options === true) {
				$this.addClass('ui-loading');
			} else {
				$this.removeClass('ui-loading');
			}
			return $this;
		};
	}
	
	/*
	 * 将Form数据转化为JSON字符串
	 */
	$.fn.f2s = function() {
		return JSON.stringify($(this).f2j());
	};

	$.fn.checkRequired = function(){
		var $this = $(this);
		$this.find('textarea.required,input.required,select.required,.ui-select-button.required').trigger('blur');
		return !$this.find('.error').length;
	};
	var CN_ZH_PATTERN = /[\u4E00-\u9FBF]/;
	/**
	 * @param date
	 *            (Date对象) 需要格式化的时间 
	 *            
	 * @param ptn           
	 *            格式化标准，如yyyy-MM-dd hh:mm:ss
	 *            支持
	 *            1. yyyy 	年份
	 *            2. MM 	月份
	 *            3. dd		日期
	 *            4. hh		小时
	 *            5. mm		分
	 *            6. ss		秒
	 *            7. SSS	毫秒
	 *            8. q		季度
	 */
	$.formatDate = function(date, ptn) {
		if (typeof date === 'string') {
			ptn = date;
			date = new Date();
		}
		ptn = ptn || "yyyy年MM月dd日 hh:mm";
		date = date || new Date();
		var dt = {
			// 年份
			"yyyy" : date.getFullYear(),
			// 月份
			"MM" : date.getMonth() + 1,
			// 日
			"dd" : date.getDate(),
			// 小时
			"hh" : date.getHours(),
			// 分
			"mm" : date.getMinutes(),
			// 秒
			"ss" : date.getSeconds(),
			// 季度
			"q" : Math.floor((date.getMonth() + 3) / 3),
			// 毫秒
			"SSS" : date.getMilliseconds()
		};
		dt.mm = padLeft(dt.mm, 2, '0');
		if (!CN_ZH_PATTERN.test(ptn)) {
			dt.MM = padLeft(dt.MM, 2, '0');
			dt.dd = padLeft(dt.dd, 2, '0');
		}
		for ( var key in dt) {
			ptn = ptn.replace(key, dt[key]);
		}
		return ptn;
	};
	function padLeft(s, l, c) {
		s = '' + s;
		if (l < s.length)
			return s;
		else
			return Array(l - s.length + 1).join(c || ' ') + s;
	}
	
	/**
	 * @param qstr(string)
	 *            	需要获取参数的URL
	 *            
	 * @return(JSON)
	 * 				返回参数值JSON对象
	 */
	$.ogetQueryParams = function(qstr) {
		qstr = qstr || location.search;
		var index = qstr.indexOf('?'), //
		params = {};
		if (index != -1) {
			qstr = qstr.substring(index + 1);
		}
		if (qstr) {
			qstr = qstr.split('&');
			for ( var i in qstr) {
				var kv = qstr[i].split('=');
				params[kv[0]] = kv[1] || '';
			}
		}
		return params;
	};
	$.getQueryParams = function(qstr) {
		qstr = qstr || location.hash;
		var index = qstr.indexOf('#'), //
		params = {};
		if (index != -1) {
			qstr = qstr.substring(index + 1);
		}
		if (qstr) {
			qstr = qstr.split('&');
			for ( var i in qstr) {
				var kv = qstr[i].split('=');
				params[kv[0]] = kv[1] || '';
			}
		}
		return params;
	};
	$.str2json = function(str) {
		if (typeof str !== 'string') {
			return str;
		}
		return jQuery.parseJSON(str);
	};
	$.clearJSON = function(json) {
		if ($.isPlainObject(json)) {
			for ( var key in json) {
				json[key] = '';
			}
		}
	};
	
	//override dialog，更改默认设置
	$.fn._dialog_ = $.fn.dialog;
	$.fn.dialog = function(opts){
		 if (typeof opts === "string") {
			 this._dialog_.apply(this, arguments);
		 } else{
			 opts.closeText = "关闭";
			 this._dialog_(opts);
		 }
		 return this;
	 };

	/**
	 * @param opts(string/JSON)
	 *            	String	:	提示信息内容（默认为提示）
	 *				JSON	:	生成提示框参数
	 *            		包括：
	 *						1.	msg		:	信息内容
	 *						2.	type	:	提示框类型，可选：alert/confirm/info/error
	 *						3.	title 	:	提示框标题，默认：警告/确认/提示/错误
	 * @return(jQuery Object)
	 *				返回对话框的jQuery对象
	 */
	$.msg = function(opts) {
		if (!$.isPlainObject(opts)) {
			opts = {
				msg : opts
			};
		}
		opts = $.extend({
			buttons : opts.type === 'confirm' ? {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy");
				},
				"取消" : function() {
					if($.isFunction(opts.cancel)){
						opts.cancel.apply(this);
					}
					$(this).dialog("destroy");
				}
			} : {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy");
				}
			}
		}, opts);
		if (opts.type == 'alert') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-alert',
				title : '警告'
			}, opts);
		} else if (opts.type == 'confirm') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-confirm',
				title : '确认',
				modal: true
			}, opts);
		} else if (opts.type == 'error') {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-error',
				title : '错误'
			}, opts);
		} else {
			opts = $.extend({
				dialogClass : 'nf-dialog nf-info',
				title : '提示'
			}, opts);
		}
		var nopts = $.extend({}, {
			title : '警告',
			resizable : false,
			minHeight : 14,
			modal : opts.modal,
			buttons : {
				"确定" : function() {
					opts.ok && opts.ok.apply(this);
					$(this).dialog("destroy").remove();
				}
			},
			close : function() {
				opts.type === 'confirm' && $.isFunction(opts.cancel) && opts.cancel.apply(this);
				opts.type === 'info' && $.isFunction(opts.ok) && opts.ok.apply(this);
				$(this).dialog("destroy").remove();
			}
		}, opts);
		return $('<div>').html(opts.msg).dialog(nopts);
	};
	
	
	var Error = {
		generateList : function(list) {
			var $ul = $('<ul class="error-list">');
			for ( var i = 0; i < list.length; i++) {
				var $li = $('<li>'), error = list[i];
				$li.text(error.message === '' ? '未知错误' : error.message);
				$ul.append($li);
			}
			$ul.hide().appendTo('body');
			return $ul.dialog({
				dialogClass : 'nf-dialog nf-error',
				title : '错误',
				minHeight : 14,
				minWidth : $ul.outerWidth() * 1.4,
				close : function() {
					$(this).dialog("destroy").remove();
				},
				buttons : {
					"确定" : function() {
						$(this).dialog("destroy").remove();
					}
				}
			});
		}
	};
	
	$.fn.key = function(opts){
		$(this).keypress(function(e){
			if(e.keyCode == 13){
				opts.entry && opts.entry.apply(this,[e]);
			}
		});
	};
	

	var AJAX = {
		error : function(options, jqXHR, tStatus, errorThrown, args) {
			options.error && options.error.apply(this, args);
			// TODO abort类型重新处理
			//如果是401，表明token过期需要重新登录
			if(jqXHR.status == '401') {
				Desktop.jumpLogin && Desktop.jumpLogin(true);
				return;
			}
			if (!options.hideError) {
				$.msg({
					type : 'error',
					msg : '调用服务[' + (options.name || '') + ']失败，'
							+ ('timeout' === tStatus ? 
								'请求超时' : ('abort' === tStatus 
									? '求情已取消' : ('parsererror' === tStatus 
										? '解析错误' : ('parsererror' === tStatus 
											? '服务器错误' : '服务器错误[' + jqXHR.status + ']'))))
				});
			}
		},
		success : function(data, options, args) {
			if (data.success) {
				options.success && options.success.apply(this, args);
			} else {
				options.fail && options.fail.apply(this, args);
				if (!options.hideError) {
					Error.generateList(data.errors);
				}
			}
		},
		getURL : function(url) {
			return (url.indexOf('http://') && url.indexOf('https://')) ? window.ROOT + url : url;
		},
		removeMask : function(opts) {
			var $el = $(opts.el);
			if ($el.is('body')) {
				$el.removeClass('ui-loading-' + opts.identify);
				AJAX.handler && clearTimeout(AJAX.handler);
				AJAX.handler = setTimeout(function() {
					if (!$el.is('[class*="ui-loading-"]')) {
						$el.mask(false);
					}
				}, 200);
			}else{
				$el.mask(false);
			}
		},
		addMask : function(opts) {
			var $el = $(opts.el).mask();
			if ($el.is('body')) {
				$el.addClass('ui-loading-' + opts.identify);
			}
		}
	};

	$.ajaxMultiForm = function(options) {
		var opts = {
			el : 'body',
			identify : $.now()
		};
		AJAX.addMask(opts);
		var data = new FormData();
		for ( var key in options.data) {
			data.append(key, options.data[key]);
		}
		$.ajax({
			url : AJAX.getURL(options.url),
			type : 'POST',
			contentType : false,
			processData : false,
			data : data,
			dataType : 'json',
			success : function(data) {
				AJAX.removeMask(opts);
				AJAX.success(data, options, arguments);
			},
			error : function(jqXHR, tStatus, errorThrown) {
				AJAX.removeMask(opts);
				AJAX.error(options, jqXHR, tStatus, errorThrown, arguments);
			}
		});
	};
	
	/**
	 * @param options(JSON)
	 *            JSON : AJAX参数(基于jQuery.ajax) 扩展属性包括： 
	 *				1. data			:	发送数据（可以是JSON字符串或JSON对象,会自动转换为字符串）
	 *				2. name			: 	定义此次请求的名称（错误提示）
	 *				3. fail			：	返回data.success为false时执行
	 *				4. success		：	返回data.success为true时执行
	 *            	5. url			：	不以http或者https开头时，自动填充服务器地址（配置在desktop-utils.js中），否则直接访问url
	 *            	6. type			：	默认为POST
	 *            	7. contentType	： 	默认为JSON
	 *              8. hideError	:	隐藏错误信息
	 * @return(jQuery Ajax Object) 返回jQuery Ajax对象
	 */
	$.ajaxJSON = function(options) {
		var opts = {
				el : 'body',
				identify : $.now()
			};
		opts = $.extend(opts, options, {
			error : function(jqXHR, tStatus, errorThrown) {
				AJAX.removeMask(opts);
				AJAX.error(options, jqXHR, tStatus, errorThrown, arguments);
			},
			success : function(data) {
				AJAX.removeMask(opts);
				AJAX.success(data, options, arguments);
			},
			dataType : 'json',
			type : options.type ? options.type : 'POST',
			contentType : 'application/json; charset=UTF-8',
			url : AJAX.getURL(options.url),
			data : options.data 
						? ($.isPlainObject(options.data) 
								? JSON.stringify(options.data) : 
									options.data): JSON.stringify({})
		});
		AJAX.addMask(opts);
		return $.ajax(opts);
	};

	/**
	 * 下载文件
	 * @param  options(JSON)
	 *         1、data：数据
	 *         2、url：路径
	 *         3、fileName: 默认另存为的文件名如“活跃客户统计表2013-1-1~2013-8-1.xls”
	 *         4、fail
	 *         5、success
	 *         6、hideError
	 *         7、name
	 */
	$.dlFile = function(options) {
		var opts = {
			el : 'body',
			identify : $.now()	
		};
		AJAX.addMask(opts);
		var url = AJAX.getURL(options.url);
		var xhr = new XMLHttpRequest();
		xhr.open('POST', url);
		xhr.responseType = 'blob';
		// xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
		// xhr.send($.param($.extend(true, {token : window.token}, options.data)));
		
		xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
		xhr.send(JSON.stringify($.extend(true, {token : window.token}, options.data))); 

		xhr.onreadystatechange = function(){
		    if (this.readyState == 4){
		    	if(this.status == 401) {
					Desktop.jumpLogin && Desktop.jumpLogin(true);
					return;
		    	}
		    	else if(this.status == 200) {
		    		AJAX.removeMask(opts);
		    		//后台返回非文件，说明是错误
		    		if(this.getResponseHeader('Content-Type').indexOf('text/plain') == 0) {
		    			var fr = new FileReader();
		    			fr.readAsText(this.response);
		    			fr.onload = function() {
		    				AJAX.success(JSON.parse(fr.result), options);
		    			};
			    		return;
			    	}
			    	//否则下载文件
			    	else {
			    		var URLHtml5 = window.URL || window.webkitURL;
				        var href = URLHtml5.createObjectURL(this.response);
				        var fileName = options.fileName ? options.fileName : "报表.xls";

				    	$dllink = $('<a href="' + href + '" download="' + fileName + '" style="display:none"></a>').appendTo(document.body);

				        var event = document.createEvent("MouseEvents");
						event.initMouseEvent(
							"click", true, false, window, 0, 0, 0, 0, 0
							, false, false, false, false, 0, null
						);
						$dllink[0].dispatchEvent(event);
				        //URLHtml5.revokeObjectURL(href);
			    	}
		    	}
		    }
		};
	};
	var	authStrReg = /[A-Za-z\-_\s0-9]+/g;
	window.App = {
		options : $.getQueryParams()
	};
	window.App = {
			close : function() {
				if (Desktop) {
					Desktop.close(window.App.options.wid);
				} else {
					close();
				}
			},
			options : window.App.options,
			API : Desktop.getAPI && Desktop.getAPI(window.App.options.wid),
			//获取当前系统配置，如是否发送短信
			getSysConfig: function() {
				return Desktop.getSysConfig() || {};
			},
			//获取当前用户
			getCurrentUser: function() {
				return Desktop.getCurrentUser() || {};
			},
			//是否是加盟店
			isJiaMengDian: function() {
				return  this.getCurrentUser().organizationLevel == 6 && this.getCurrentUser().organizationType == 'J';
			},
			//是否是直营店
			isZhiYingDian: function() {
				return this.getCurrentUser().organizationLevel == 6 && this.getCurrentUser().organizationType == 'Z';
			},
			//获取当前app的权限，主要用于外部自定义权限ui刷新
			getAuth: function() {
				return Desktop.getAuth(window.App.options.alias);
			},
			//默认的刷新权限ui方法
			freshAuthUI: function(AUTH_MODULES) {
				var appAuth =  this.getAuth();
				for(var moduleId in AUTH_MODULES) {
					//是否有权限操作该module
					var hasAuth = false;
					for (var i = 0; i < appAuth.modules.length; i++) {
						var module = appAuth.modules[i];
						if(moduleId == module.moduleId) {
							hasAuth = true;
							break;
						}
					};
					//fresh UI
					$moduleUI = $(AUTH_MODULES[moduleId]);
					// if(hasAuth && $moduleUI.hasClass('hide')){
					// 	$moduleUI.show();
					// }
					if(!hasAuth) {
						$moduleUI.hide();
					}
				}
			},
			hasAuthName : function(authName){
				var modules = this.getAuth().modules;
				authName = $.trim(authName);
				for (var i = 0, len = modules.length; i < len; i++) {
					if (modules[i].functions.indexOf(authName) >= 0) {
						return true;
					}
					continue;
				}
				return false;
			},
			getCities: function(callback) {
				return Desktop.getCities(callback);
			},
			//权限控制语句解析
			parseAuthLan : function(str){
				return (new Function('return ' + str.replace(authStrReg, function(s){
					return App.hasAuthName(s);
				})))();
			}
		};
	if (window.Desktop) {
		if (!window.ignoreLoading) {
			if (window.App.options && window.App.options['wid']) {
				Desktop.moveLoadingFlag && Desktop.moveLoadingFlag(window.App.options['wid']);
			}
		}
	}
	$('[data-auth]').each(function(){
		var $this = $(this);
		if (!App.parseAuthLan($this.attr('data-auth'))) {
			$this.remove();
		}
	});
	//转义字符换成普通字符
	$.escape2Html = function(str) {
		if(str == null || str == "") return;
		var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
		return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	};	
})(window, jQuery);

$(function() {
	//粘贴验证
	var PasteValidation = {
			"ht-number" : /^\d*$/,
			"ht-decimal": /^\d*(\.\d+)?$/
	};
	$.each(PasteValidation, function(i ,n){
		$("body").on("paste","input."+i, function(e){
			var p = $.trim(e.originalEvent.clipboardData.getData("text/plain"));
			return n.test(p);
		});
	});
	//select wedgit
	$('.ht-select').select();
	// 8        - 退格 
    // 9        - Tab 
    // 13       - 回车 
    // 16~18    - Shift, Ctrl, Alt 
    // 37~40    - 左上右下 
    // 35~36    - End Home 
    // 46       - Del 
    // 112~123  - F1-F12 
	var KEY = /[\x08\x09\x0D\x10\x11\x12\x23\x24\x25\x26\x27\x28\x2E\x70\x71\x72\x73\x74\x75\x76\x77\x78\x79\x7A\x7B]/,
		BDKEY = /[\x58\x52\x43\x41]/;
	$('body').on('keydown', 'input.ht-number', function(e) {
		var code = e.keyCode;
    	if (KEY.test(String.fromCharCode(code)) || (BDKEY.test(String.fromCharCode(code)) && e.ctrlKey)) {
			return true;
		}
    	if (e.ctrlKey && code === 86) {
    		return true;
		}
		if (((code > 47 && code < 58) || (code > 95 && code < 106)) && !e.shiftKey) {
			return true;
		}
		return false;
	});
	$('body').on('keydown', 'input.ht-negative', function(e) {
		var code = e.keyCode;
    	if (KEY.test(String.fromCharCode(code)) || (BDKEY.test(String.fromCharCode(code)) && e.ctrlKey)) {
			return true;
		}
    	if (e.ctrlKey && code === 86) {
    		return true;
		}
		if (((code > 47 && code < 58) || (code > 95 && code < 106)) && !e.shiftKey) {
			return true;
		}
		if ((code == 109 || code == 173) && !e.shiftKey) {
			if ($(this).val() == "") {
				return true;
			}
			return false;
		}
		return false;
	});
	$('body').on('keydown', 'input.ht-decimal', function(e) {
		var code = e.keyCode;
		if (KEY.test(String.fromCharCode(code)) || (BDKEY.test(String.fromCharCode(code)) && e.ctrlKey)) {
			return true;
		}
		if (e.ctrlKey && code === 86) {
			return true;
		}
		if (((code > 47 && code < 58) || (code > 95 && code < 106)) && !e.shiftKey) {
			return true;
		}
		if ((code == 110 || code == 190) && !e.shiftKey) {
			if ($(this).val() == "") {
				return false;
			} 
			if ($(this).val().indexOf('.') != -1) {
				return false;
			}
			return true;
		}
		return false;
	});
	$('body').on('blur', 'textarea.required,input.required,select.required,.ui-select-button.required', function(e) {
		var $tag = $(this), $el = $tag;
		if ($tag.is('.ui-select-button')) {
			if(e.isSimulated) return;
			$el = $tag.prev();
		}
		if ($el.val()) {
			$tag.removeClass('error');
		} else {
			$tag.addClass('error');
		}
	});
});
