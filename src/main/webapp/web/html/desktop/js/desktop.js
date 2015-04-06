(function(window, $) {
	window.token = $.getQueryParams().token;
//	if(!window.token) {
//		$.msg('请先登录，两秒后自动跳转到登陆页面');
//		setTimeout(function() {
//			location.href = "../login/login.html";
//		}, 2000);
//		return;
//	}

	var $winCtn = $('.desktop-container'), //
		$winbox = $($.trim($('#window-tpl').html())), //
		$taskappbox = $($.trim($('#task-app-tpl').html())), //
		$appctn = $('.app-container'), //
		$taskbar = $('.task-bar'), //
		zCount = 1000, //
		URL = {
			GET_AUTH : "web/json/apps.text"
		}, //
		DATA_PATTERN = 'yyyy年MM月dd日 hh:mm';
	var DESK_PAD = {
						top : 120,
						bottom : 50,
						left : 150,
						right : 100
					}, //
		WIN = {
			width : $(document).width(),
			height : $(document).height(),
		}, //
		APP = {
			width : 150,
			height : 150
		}, //
		TASK_APP_PREFIX = 'task-app-', //
		WIN_PREFIX = 'win-', //
		AXIS = 'X';
	
	//当前用户信息
	var User = {
		logout: function() {
			$.ajaxJSON({
				name : '退出',
				url : URL.LOGOUT,
				data : {},
				success : function(data) {
					$.msg('成功退出，两秒后自动跳转到登陆页面');
					setTimeout(function() {
						location.href = "../login/login.html";
					}, 2000);
				}
			});
		},
		getUserInfo: function() {
			
		},
		init: function() {
			this.getUserInfo();
			$('#logout a').click(function() {
				$.msg({
					type: 'confirm',
					msg: '确定退出？',
					ok: function() {
						User.logout();
					}
				});
			});
		}
	};
	User.init();
	var SysConfig = {
		getConfig: function() {
			
		},
		init: function() {
			this.getConfig();
		}
	};
	SysConfig.init();
	//权限相关
	var Auth = {
		getAuth : function() {
			
			$.ajaxJSON({
				name: '获取用户权限',
				url : URL.GET_AUTH,
				dataType : "text",
				contentType : "text/plain",
				type : "get",
				data : "",
				success : function(data) {
					//apps modules functions
					//init app;
					var $appCtn = $('.app-container');
					for (var i = 0; i < data.apps.length; i++) {
						var app = data.apps[i];
						//insert html
						$app = $('<div class="app-box">' + 
									'<div class="app-img">' + 
										'<img alt="' + app.appName + '" src="../apps/' + app.alias + '/images/desktop-icon.png">' + 
									'</div>' +
									'<i class="app-title">' + app.appName + '</i>' + 
								'</div>');
						$app.data({
							id : app.appId,
							alias : app.alias,
							width: app.width,
							height: app.height,
							max: app.max,
							resizable: app.resizable,
							link : app.link == '#' ? '' : app.link,
							src : app.src
						});
						$appCtn.append($app);
					};
					App.resetApps($('.app-box'));
				}
			});
		}
	};
	Auth.getAuth();
	var Tool = {
		bsPrefix : (function() {
			if (document.cancelFullScreen) {
				return '';
			}
			var pfs = 'webkit moz o ms khtml'.split(' ');
			for ( var i = 0; i < pfs.length; i++) {
				if (document[pfs[i] + 'CancelFullScreen']) {
					return pfs[i];
				}
			}
		})(),
		getCities : function(callback) {
			
		}
	};
	Tool.getCities();
	
	// END OF TOOL INITIALIZATION
	// var $msgList = $('.message-list ul');
	// setInterval(function() {
	// 	$msgList.find('li:eq(0)').animate({
	// 		marginTop : -30
	// 	}, function() {
	// 		$(this).hide().appendTo($msgList).css('marginTop', 0).show();
	// 	});
	// }, 2500);
	
	// START OF APP LIST
	var App = {
		resetApps : function($apps) {
			var row = Math.floor((WIN.height - DESK_PAD.top - DESK_PAD.bottom) / APP.height) || 1, //
			col = Math.floor((WIN.width - DESK_PAD.left - DESK_PAD.right) / APP.width) || 1; //
			if (AXIS == 'X') {
				$apps.each(function(i) {
					var $this = $(this);
					$this.css({
						left : DESK_PAD.left + i % col * APP.height,
						top : DESK_PAD.top + parseInt(i / col) * APP.width
					});
				});
			} else {
				$apps.each(function(i) {
					var $this = $(this);
					$this.css({
						top : DESK_PAD.top + i % row * APP.width,
						left : DESK_PAD.left + parseInt(i / row) * APP.height
					});
				});
			}
		}
	};
//点击App弹出窗口
	$('.app-container').on('click', '.app-box', function() {
		var $this = $(this), data = $this.data();
		// Add win in desktop
		//console.log(data);
		Win.open({
			id : data.id,
			src : data.link,
			alias : data.alias,
			title : $this.find('i').text(),
			height : data.height,
			width : data.width,
			max : data.max,
			delay : data.delay,
			resizable: data.resizable
		});
	});
	$appctn.sortable({
		items : '.app-box:not(.app-add)',
		containment : 'parent',
//		revert : 200,
		connectWith : '.app-container',
		appendTo : document.body,
		helper : 'clone',
		change : function(e, ui) {
			App.resetApps($(this).find('.app-box').not(ui.item));
		},
		stop : function() {
			App.resetApps($(this).find('.app-box'));
		},
		start : function(e, ui) {
			App.resetApps($(this).find('.app-box').not(ui.item));
		}
	});
	$appctn.each(function(i, e) {
		App.resetApps($(this).find('.app-box'));
		$(this).show();
	});
	// END OF APP LIST

	// START OF WINDOW
	var Win = {
		open : function(opts) {
			opts = $.extend({
				taskFlag : true,
				min : true,
				max : false,
				multiple : false,
				loading : true,
				draggable: true,
				resizable: true
			}, opts);
			var id = opts.multiple ? $.now() : opts.id, //
				winId = WIN_PREFIX + id, //
				taskId = TASK_APP_PREFIX + id,
 				appOpts = $.param({
					wid : id,
					aid : opts.id,
					alias : opts.alias
				});
			if (!opts.multiple && $('#' + winId).length !== 0) {
				$('#' + winId).css('zIndex', zCount++).show();
				$('#' + taskId).hide();
				return;
			}
			opts.src = opts.src
			? (opts.src.indexOf('#') > 0 
					? opts.src + '&' + appOpts 
							: opts.src + '#' + appOpts) 
							: (opts.alias 
									? '../apps/' + opts.alias + '/html/index.html#' + appOpts 
											: '');
			if (opts.alias == 'site') {
				window.open(opts.src);
				return;
			}
			var $win = $winbox.clone();
			$win.data('api', opts.api),
			$winCtn.find('.app-win').removeClass('app-win-active');
			$win.find('.app-win-title').text(opts.title);
			$win.find('iframe').attr({
				'src' : opts.src,
				'name' : winId
			}).hide();
			opts.width = opts.width || 900;
			opts.height = opts.height || 600;
			opts.top = Math.max(opts.top || ($(document).height() - opts.height) / 2, 0);
			opts.left = Math.max(opts.left || ($(document).width() - opts.width) / 2, 0);
			$win.data({
				id : id
			}).css({
				zIndex : zCount++,
				width : opts.width,
				height : opts.height,
				top : opts.top,
				left : opts.left,
				position : 'absolute'
			}).attr({
				id : winId
			}).addClass('app-win-active');
			if(opts.draggable){
				$win.draggable({
					handle : '.app-win-title-bar',
					containment : 'body'
				});
			}
			
			if( opts.resizable ) {
				$win.resizable({
					handles : 'n,s',
					containment : 'body'
				});
			}

			if (opts.max) {
				$win.find('.app-win-btn-max').show();
			}
			if (!opts.min) {
				$win.find('.app-win-btn-min').hide();
			}
			if (!opts.loading) {
				$win.removeClass('loading');
			}
			
			// common methods, pull them up
			$win.bind('mousedown', function() {
				$win.css('zIndex', zCount++);
				$('.app-win', '.desktop-container').removeClass('app-win-active');
				$win.addClass('app-win-active');
			});

			$win.find('.app-win-btn-min').click(function(e) {
				Win.min($(this).parents('.app-win'));
			});
			$win.find('.app-win-btn-close').click(function(e) {
				Win.close($(this).parents('.app-win'));
			});
			
			if (opts.max) {
				$win.find('.app-win-btn-max').click(function(e) {
					Win.max($(this).parents('.app-win'));
				});
				$win.find('.app-win-title-bar').dblclick(function(e) {
					Win.max($(this).parents('.app-win'));
				});
			}
			
			$winCtn.append($win);
			
			// Add app in taskbar
			if (opts.taskFlag) {
				var $task = $taskappbox.clone().hide();
				$task.data({
					id : id
				}).attr('id', TASK_APP_PREFIX + id).find('span').text(opts.title);
				$taskbar.append($task);
			}
			setTimeout(function(){
				$($win.find("iframe")[0].contentWindow).focus(function(){
					$(this.top.window).click();
				});
			},100);
		},
		max : function($win) {
			var orgData = $win.data('org-data');
			if (orgData) {
				$win.css(orgData);
				$win.removeData('org-data');
				$win.find('.app-win-btn-max').attr('title', '最大化');
			} else {
				$win.data('org-data', $.extend({
					width : $win.width(),
					height : $win.height()
				}, $win.offset()));
				$win.css({
					left : 0,
					top : 0,
					width : $(document).width(),
					height : $(document).height()
				});
				$win.find('.app-win-btn-max').attr('title', '还原');
			}
		},
		min : function($win) {
			$win.toggle();
			if ($win.is('.app-win-active') && $win.is(':hidden')) {
				$win.removeClass('app-win-active');
				Win.setTopActive();
			}
			$('#' + TASK_APP_PREFIX + $win.data('id')).toggle();
		},
		setTopActive : function() {
			var $new;
			$('.app-win:visible').each(function() {
				if (!$new || $(this).css('zIndex') > $new.css('zIndex')) {
					$new = $(this);
				}
			});
			$new && $new.addClass('app-win-active');
		},
		close : function($win) {
			var wid = $win.data('id'),
				fwin = frames[WIN_PREFIX+wid];
			try{
				var AppUtil = fwin && fwin.App;
			}catch(e){}
			if (!AppUtil || !AppUtil.onClose || AppUtil.onClose()) {
				$('#' + TASK_APP_PREFIX + $win.data('id')).remove();
				$win.remove();
				Win.setTopActive();
			}
		}
	};
	// END OF WINDOW
	
	// open API for user and system
	var Desktop = {
		/*
		 * @param opts{JSON}
		 *			taskFlag 	: 默认值为true, 显示任务栏图标
		 *			max 		: 默认值为true, 显示最大化按钮
		 *			min 		: 默认值为true, 显示最小化按钮
		 *			multiple 	: 默认值为false, 支持应用多窗口
		 */
		open : Win.open,
		moveLoadingFlag : function(wid) {
			$('#' + WIN_PREFIX + wid).removeClass('loading').find('iframe').show();
		},
		close : function(wid) {
			Win.close($('#' + WIN_PREFIX + wid));
		},
		getAPI : function(wid) {
			$('#' + WIN_PREFIX + wid).data('api');
		},
		jumpLogin : function(isFromApp) {
			
		},
		getCities : function(callback) {
			if(!callback) {
				return Tool.CITIES;
			}
			else {
				if(Tool.CITIES) {
					callback(Tool.CITIES);
				}
				else {
					Tool.getCities(callback);
				}
			}
		},
		//获取某app的子权限
		getAuth: function(alias) {
			for (var i = 0; i < Auth.apps.length; i++) {
				var app = Auth.apps[i];
				if(app.alias == alias) {
					return app;
				}
			};
			return null;
		},
		//获取系统配置
		getSysConfig: function() {
			return SysConfig.config;
		},
		//获取当前用户
		getCurrentUser: function() {
			return User.currentUser;
		},
		blinkTitle: function(title) {
			this.unblinkTitle();
			window.originTitle = document.title;
			window.blinkTitleIntervalId = setInterval(function(){
				document.title = (document.title == title ? '注意' : title);
			}, 1000);
		},
		unblinkTitle: function() {
			if(window.blinkTitleIntervalId) {
				clearInterval(window.blinkTitleIntervalId);
				delete window.blinkTitleIntervalId;
				document.title = window.originTitle;
			}
		},
		token: token ? token : ''
	};
	$(window).blur(function() {
		window.isFocus = false;
	});
	$(window).click(function(){
		window.isFocus = true;
		Desktop.unblinkTitle();
		if(window.notifyAudio) {
			window.notifyAudio.pause();
		}
	});
	window.isFocus = true;
	//请求桌面通知授权
	Notification.requestPermission();
	
	$(window).resize(function() {
		WIN = {
			width : $(document).width(),
			height : $(document).height(),
		};
		$appctn.each(function(i, e) {
			App.resetApps($(this).find('.app-box'));
		});
		$('.app-win').each(function() {
			var $win = $(this);
			if ($win.data('orgData')) {
				$win.css({
					left : 0,
					top : 0,
					width : $(document).width(),
					height : $(document).height()
				});
			}
		});
	});
	
	window.Desktop = Desktop;
	window.des = 'desktop';
	
	// START OF TASKBAR
	$('.task-bar').on('click', '.icon-close', function() {
		var $app = $(this).parents('.task-app');
		Win.close($('#' + WIN_PREFIX + $app.data('id')));
	});

	$('.task-bar').on('click', '.task-app', function() {
		var $win = $('#' + WIN_PREFIX + $(this).data('id'));
		$('.app-win-active').removeClass('app-win-active');
		$win.css('zIndex', zCount++).addClass('app-win-active');
		Win.min($win);
	});
})(window, jQuery);