//페이지 이동
function _goPage(pMenuId, menuId, url, menuNm, pMenuNm) {

	//개발시 IURL을 찾기 쉽도록 호출 URL을 로그에 쓴다.
	console.log("[DEBUG]   request URL===>> " + url);

	$("#content").empty();
	$("#content").load(url);

	//접속 로그 생성
	_setSysLog(url, menuNm, menuId, 'S');
}

//로그 없이 페이지 이동
// url : 이등 URL
function _goPageWithNoLog(url) {

	//개발시 IURL을 찾기 쉽도록 호출 URL을 로그에 쓴다.
	console.log("[DEBUG]   request URL===>> " + url);

	$("#content").empty();
	$("#content").load(url);
}

// 로그없이 ajax로 form 전송하여 페이지 이동
// url : 이등 URL
// frmId : 전송할 FORM ID 값
function _goPageWithFormNoLog(url, frmId) {

	//개발시 IURL을 찾기 쉽도록 호출 URL을 로그에 쓴다.
	console.log("[DEBUG]   request URL===>> " + url);

	$.ajax({
		url: url,		//Ajax 호출은 URL에 Ajax 반드시 포함
		type: "POST",
		data: $("#" + frmId).serialize(),
		success: function(html) {
			$("#content").empty();
			$("#content").html(html);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			//세션이나 쿠키가 없을 때는 LoginCheckAspect 에서 401 에러 발생
			if (jqXHR.status == '401') {
				location.href = "/common/sessionError.do";
			}
			else {
				console.log(jqXHR.responseText);
			}
		}
	});
}


//로그없이 ajax로 form 전송하여 페이지 이동
//url : 이등 URL
//frmId : 전송할 FORM ID 값
//tabNm : 탭명
function _goTabWithNoLog(url, tabNm) {

	//개발시 IURL을 찾기 쉽도록 호출

	//URL을 로그에 쓴다.
	console.log("[DEBUG]   request URL===>> " + url);
	parent._goTabPageNoLog("000000", url, tabNm);
}

//숫자 comma삽입
function setDigitComma(val) {
	var temp = val + "";
	if (temp.indexOf(".") != -1) {
		var temp2 = temp.split(".");
		temp = temp2[0].replace(/[^-0123456789]/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "." + temp2[1].replace(/[^0123456789]/g, "");
	}
	else {
		temp = temp.replace(/[^-0123456789]/g, "");
		temp = temp.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	return temp;
}

//한글입력 금지
function onlyNumEng(obj) {
	obj.value = obj.value.replace(/[^a-z0-9]/gi, '');
}

//한글입력 금지
function onlyNumEngDotAt(obj) {
	obj.value = obj.value.replace(/[^a-z0-9@.]/gi, '');
}

//text textarea MaxLength 체크
//사이즈 초과시 false, 미초과시 true
function _checkFormMaxlength(form) {

	if (form == undefined || form == null) {
		return true;
	}

	for (var i = 0; i < form.length; i++) {

		if (form.elements[i].type.toLowerCase() == 'text' || form.elements[i].type.toLowerCase() == 'textarea') {
			if (form.elements[i].title != undefined && form.elements[i].title != null && form.elements[i].title != "" &&
				form.elements[i].maxLength != undefined && form.elements[i].maxLength != null && form.elements[i].maxLength != 0) {

				if (fnChkByte4PqGrid(form.elements[i].value.trim()) > form.elements[i].maxLength) {
					alert(form.elements[i].title + "은(는)" + form.elements[i].maxLength + "byte 까지 입력가능합니다.(" + fnChkByte4PqGrid(form.elements[i].value) + "/" + form.elements[i].maxLength + ")");
					form.elements[i].focus();
					return false;
				}
			}
		}
	}

	return true;
}

//text textarea MinLength 체크
//사이즈 초과시 false, 미초과시 true
function _checkFormMinlength(form) {

	if (form == undefined || form == null) {
		return true;
	}

	for (var i = 0; i < form.length; i++) {

		if (form.elements[i].type.toLowerCase() == 'text' || form.elements[i].type.toLowerCase() == 'textarea') {
			if (form.elements[i].title != undefined && form.elements[i].title != null && form.elements[i].title != "" &&
				$(form.elements[i]).attr("minlength") != undefined && $(form.elements[i]).attr("minlength") != null && $(form.elements[i]).attr("minlength") >= 1) {
				if (fnChkByte4PqGrid(form.elements[i].value) < 1) {
					alert(form.elements[i].title + "은(는) 필수 입력입니다.");
					form.elements[i].focus();
					return false;
				}

				if (fnChkByte4PqGrid(form.elements[i].value) < $(form.elements[i]).attr("minlength")) {
					alert(form.elements[i].title + "은(는) 최소 " + $(form.elements[i]).attr("minlength") + "자리로 입력해야 합니다.");
					form.elements[i].focus();
					return false;
				}

			}
		}
	}

	return true;
}


function gfn_isNull(str) {
	if (str == null) return true;
	if (str == "NaN") return true;
	if (new String(str).valueOf() == "undefined") return true;
	var chkStr = new String(str);
	if (chkStr.valueOf() == "undefined") return true;
	if (chkStr == null) return true;
	if (chkStr.toString().length == 0) return true;
	return false;
}

function ComSubmit(opt_formId) {
	this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
	this.url = "";

	if (this.formId == "commonForm") {
		//$("#commonForm")[0].reset();
		$("#" + this.formId).empty();
	}

	this.setUrl = function setUrl(url) {
		this.url = url;
	};

	this.setTarget = function setTarget(target) {
		this.target = target;
	};

	this.addParam = function addParam(key, value) {
		$("#" + this.formId).append($("<input type='hidden' name='" + key + "' id='" + key + "' value='" + value + "' >"));
	};

	this.submit = function submit() {
		var frm = $("#" + this.formId)[0];
		frm.action = this.url;
		if (this.target != null)
			frm.target = this.target;
		frm.method = "post";
		frm.submit();
	};
}


//object id값으로 이동하기
function fnMove(elId) {
	var offset = $("#" + elId).offset();
	$('html, body').animate({ scrollTop: offset.top }, 400);
}

function fnChkByte(obj, limitByte, targetObjId) {
	var maxByte = limitByte; //최대 입력 바이트 수
	var str = obj.value;
	var str_len = str.length;

	var rbyte = 0;
	var rlen = 0;
	var one_char = "";
	var str2 = "";

	for (var i = 0; i < str_len; i++) {
		one_char = str.charAt(i);

		if (escape(one_char).length > 4) {
			rbyte += 2; //한글2Byte
		} else {
			rbyte++; //영문 등 나머지 1Byte
		}

		if (rbyte <= maxByte) {
			rlen = i + 1; //return할 문자열 갯수
		}
	}

	if (rbyte > maxByte) {
		alert("한글 " + (maxByte / 2) + "자 / 영문 " + maxByte + "자를 초과 입력할 수 없습니다.");
		str2 = str.substr(0, rlen); //문자열 자르기
		obj.value = str2;
		fnChkByte(obj, maxByte, targetObjId);
	} else {
		if (targetObjId != "")
			document.getElementById(targetObjId).innerText = rbyte;
	}
}

/*
 * 숫자만 입력 onlyNumber 버그로 재작성 숫자가 아닐경우 무시됨
 */
function checkNumber(event) {
	event.target.value = event.target.value.replace(/[^0-9]/g, "");
}
/*
 * 숫자만 입력
 * <input type="text" name="num" onkeydown="return onlyNumber(event);" onkeyup="removeChar(event);" style="ime-mode:disabled;" />
 * */
function onlyNumber(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;
	// 8 : backspace,    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    46 : del,    35 : End,    36 : Home
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 37 || keyID == 39 || keyID == 46 || keyID == 35 || keyID == 36) {
		return;

	} else {
		return false;

	}
}

/*
 * 숫자와 . 입력
 * <input type="text" name="num" onkeydown="return onlyNumber(event);" onkeyup="removeChar(event);" style="ime-mode:disabled;" />
 * */
function onlyNumberWithDot(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;
	// 8 : backspace,    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    46 : del,    35 : End,    36 : Home.  110 190 : .
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 37 || keyID == 39 || keyID == 46 || keyID == 35 || keyID == 36 || keyID == 110 || keyID == 190) {
		//.은 한번만 가능
		if (event.srcElement.value.indexOf(".") != -1 && (keyID == 110 || keyID == 190)) {
			return false;
		}
		return;

	} else {
		return false;

	}
}

//자리수 체크
function checkLengthPoint(event, digit) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;

	//숫자를 입력했을 때
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105)) {
		if (event.target.value.length > digit) {
			alert(digit + "자리수 까지 입력가능합니다.");
			event.target.value = event.target.value.substr(0, event.target.value.length - 1);
		}
	}
}

//소수점 체크
function checkDecimalPoint(event, digit) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//숫자를 입력했을 때
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105)) {
		if (event.target.value.indexOf(".") != -1) {
			if ((event.target.value.split(".")[1]).length == digit) {
				alert("소수점 " + digit + "까지 입력가능합니다.");
				event.target.value = event.target.value.substr(0, event.target.value.length - 1);
			}
		}
	}
}



/*
 * 숫자만 입력( 소수점 허용 )
 * <input type="text" name="num" onkeydown="return onlyNumber(event);" onkeyup="removeChar(event);" style="ime-mode:disabled;" />
 * */
function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if ((charCode >= 48 && charCode <= 57) || (charCode >= 96 && charCode <= 105) || charCode == 8 || charCode == 9 || charCode == 46 || charCode == 37 || charCode == 39) {
		return;
	} else {
		return false;
	}


	// Textbox value       
	var _value = event.srcElement.value;

	// 소수점(.)이 두번 이상 나오지 못하게
	var _pattern0 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
	if (_pattern0.test(_value)) {
		if (charCode == 46) {
			return true;
		}
	}

	// 소수점 둘째자리까지만 입력가능
	var _pattern2 = /^\d*[.]\d{2}$/; // 현재 value값이 소수점 둘째짜리 숫자이면 더이상 입력 불가
	if (_pattern2.test(_value)) {
		alert("소수점 둘째자리까지만 입력가능합니다.");
		return false;
	}

	return true;
}

function removeChar(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//if ( keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ) {
	//  제외 숫자 와 keyCode ==>  8 : backspace,    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    46 : del,    35 : End,    36 : Home
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 16 || keyID == 37 || keyID == 39 || keyID == 46 || keyID == 35 || keyID == 36) {
		return;

	} else {
		event.target.value = event.target.value.replace(/[^0-9]/g, "");

	}
}


/* 숫자와 .만 입력 */
function removeCharWithoutDot(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//if ( keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ) {
	//  제외 숫자 와 keyCode ==>  8 : backspace,    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    46 : del,    35 : End,    36 : Home
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 16 || keyID == 37 || keyID == 39 || keyID == 46 || keyID == 35 || keyID == 36) {
		return;

	} else {
		event.target.value = event.target.value.replace(/[^0123456789.]/g, "");

	}
}
/* 사업자등록번호 유효성 체크 */
function CheckComNo(compreg_no) {
	if (Number(compreg_no) < 10) {
		//사업자번호 없는 임시거래처는 체크 하지 않음 (0000000001:가족연, 0000000002:골프등) 
		return true;
	}
	var chkRule = "137137135";
	var strCoreNum = compreg_no;
	var step1, step2, step3, step4, step5, step6, step7;
	step1 = 0;

	for (i = 0; i < 7; i++) {
		step1 = step1 + (strCoreNum.substring(i, i + 1) * chkRule.substring(i, i + 1));
	}

	step2 = step1 % 10;
	step3 = (strCoreNum.substring(7, 8) * chkRule.substring(7, 8)) % 10;
	step4 = strCoreNum.substring(8, 9) * chkRule.substring(8, 9);
	step5 = Math.round(step4 / 10 - 0.5);
	step6 = step4 - (step5 * 10);
	step7 = (10 - ((step2 + step3 + step5 + step6) % 10)) % 10;

	if (strCoreNum.substring(9, 10) != step7) {
		return false;
	}

	return true;
}

/* 전화번호 체크 숫자와 - 만 입력가능 */
function chkTel(event) {

	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//  제외 keyCode ==>  8 : backspace,    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    46 : del,    35 : End,    36 : Home
	if (keyID != 8 && keyID != 9 && keyID != 16 && keyID != 37 && keyID != 39 && keyID != 46 && keyID != 35 && keyID != 36) {
		var _pattern0 = /[^0-9-]/g;
		if (_pattern0.test(event.target.value)) {
			alert("숫자와 - 만 입력가능 합니다.");
		}
		event.target.value = event.target.value.replace(/[^0-9-]/g, "");
	}
}

// onchange="setComma(event);" onkeyup="setComma(event);"
function setComma(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//  제외 keyCode ==> 9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    35 : End,    36 : Home
	if (keyID != 9 && keyID != 16 && keyID != 37 && keyID != 39 && keyID != 35 && keyID != 36) {
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
		event.target.value = event.target.value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
}




/* 소수점 콤마 */
function setCommaWithDot(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//  제외 keyCode ==>    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    35 : End,    36 : Home
	if (keyID != 9 && keyID != 16 && keyID != 37 && keyID != 39 && keyID != 35 && keyID != 36) {
		event.target.value = event.target.value.replace(/[^0123456789.]/g, "");
		event.target.value = event.target.value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
}


/* 백분률 소수점 콤마 0 ~ 100 사이 값만 입력 가능함*/
function setPerCommaWithDot(event) {
	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//  제외 keyCode ==>    9 : tab,    16 : shift,    37 : 왼쪽 화살표,    39 : 오른쪽 화살표,    35 : End,    36 : Home
	if (keyID != 9 && keyID != 16 && keyID != 37 && keyID != 39 && keyID != 35 && keyID != 36) {

		var _value = event.target.value.replace(/,/gi, "");
		event.target.value = _value > 100 ? 100 : _value < 0 ? 0 : _value;
		event.target.value = event.target.value.replace(/[^0123456789.]/g, "");
		event.target.value = event.target.value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");

	}
}

/* num 만큰 스크립트 실행 sleep MODAL 팝업에서 JQGRID 사용시 MODAL의 Animation 중에는 MODAL WIDTH를 정확하게 찾을 수 없어 잠시 대기 후 WIDTH를 구하기 위해 사용 */
function sleep(num) {	//[1/1000초]
	var now = new Date();
	var stop = now.getTime() + num;
	while (true) {
		now = new Date();
		if (now.getTime() > stop) {
			return;
		}
	}
}


/**
 * 문자열이 빈 문자열인지 체크하여 결과값을 리턴한다.
 * @param str       : 체크할 문자열
 */
function isEmpty(str) {

	if (typeof str == "undefined" || str == null || str == "")
		return true;
	else
		return false;
}

/**
* 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
* @param str           : 체크할 문자열
* @param defaultStr    : 문자열이 비어있을경우 리턴할 기본 문자열
*/
function nvl(str, defaultStr) {

	if (typeof str == "undefined" || str == null || str == "")
		str = defaultStr;

	return str;
}



/**
* 일자 입력시 자동 "-" 처리 및 Validation Check
* <input type="text" id="release_dt" name="release_dt"  onkeyup="javascript:date_mask(this.form.name, this.name);">
*/
function date_mask(formd, textid) {
	var form = eval("document." + formd);
	var text = eval("form." + textid);

	var textlength = text.value.length;

	if (textlength == 4) {
		text.value = text.value + "-";
	} else if (textlength == 7) {
		text.value = text.value + "-";
	} else if (textlength > 9) {
		//날짜 수동 입력 Validation 체크
		var chk_date = checkdate(text);

		if (chk_date == false) {
			return;
		}
	}
}

//date 형식 문자열로 변환 ex)20200918
function castStrDate(date) {
	var yyyy;
	var mm;
	var dd;
	var strDate;

	yyyy = date.getFullYear() + "";
	mm = date.getMonth() + 1 + "";
	if ((mm).length < 2) {
		mm = "0" + mm;
	}
	dd = date.getDate() + "";
	if ((dd).length < 2) {
		dd = "0" + dd;
	}

	strDate = yyyy + mm + dd;
	return strDate;
}

/**
* 일자 입력시 자동 "-" 처리 및 Validation Check
* <input type="text" id="release_dt" name="release_dt"  onkeyup="javascript:date_mask(this.form.name, this.name);">
*/
function checkdate(input) {
	var validformat = /^\d{4}\-\d{2}\-\d{2}$/; //Basic check for format validity
	var returnval = false;
	if (!validformat.test(input.value)) {
		alert("날짜 형식이 올바르지 않습니다. YYYY-MM-DD");
	} else { //Detailed check for valid date ranges
		var yearfield = input.value.split("-")[0];
		var monthfield = input.value.split("-")[1];
		var dayfield = input.value.split("-")[2];
		var dayobj = new Date(yearfield, monthfield - 1, dayfield);
	}
	if ((dayobj.getMonth() + 1 != monthfield)
		|| (dayobj.getDate() != dayfield)
		|| (dayobj.getFullYear() != yearfield)) {
		alert("날짜 형식이 올바르지 않습니다. YYYY-MM-DD");
	} else {
		//alert ('Correct date');
		returnval = true;
	}
	if (returnval == false) {
		input.select();
	}
	return returnval;
}

// lpad 자리수 만큼 채우기
function lpad_val(n, width, pad_val) {
	n = n + '';
	return n.length >= width ? n : new Array(width - n.length + 1).join(pad_val) + n;
}

// lpad 4자리 0으로 채우기
function lpad(n) {
	return lpad_val(n, 4, '0');
}

/*
 * wrapWindowByMask();
 * closeWindowByMask();
 */

function wrapWindowByMask() {
	//화면의 높이와 너비를 구한다.
	var maskHeight = $(document).height();
	var maskWidth = window.document.body.clientWidth;

	var mask = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
	var loadingImg = '';

	loadingImg += "<div id='loadingImg' style='position:absolute; left:50%; top:40%; display:none; z-index:10000;'>";
	loadingImg += "<img src='/img//viewLoading.gif'/>";
	loadingImg += "</div>";

	//화면에 레이어 추가
	$('body')
		.append(mask)
		.append(loadingImg)

	//마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	$('#mask').css({
		'width': maskWidth
		, 'height': maskHeight
		, 'opacity': '0.3'
	});

	//마스크 표시
	$('#mask').show();

	//로딩중 이미지 표시
	$('#loadingImg').show();
}

function closeWindowByMask() {
	$('#mask, #loadingImg').hide();
	$('#mask, #loadingImg').remove();
}

function NewWindow(mypage, myname, w, h, scroll) {
	LeftPosition = (screen.width) ? (screen.width - w) / 2 : 0;
	TopPosition = (screen.height) ? (screen.height - h) / 2 : 0;
	settings = 'height=' + h + ',width=' + w + ',top=' + TopPosition + ',left=' + LeftPosition + ',scrollbars=' + scroll + ',resizable';

	win = window.open(mypage, myname, settings)
	return win;
}

/**
 * 에러 공통 처리
 */
function commonErrFunc(jqXHR, status, error) {
	//세션이나 쿠키가 없을 때는 LoginCheckAspect 에서 401 에러 발생
	if (jqXHR.status == '401') {
		location.href = "/common/sessionError.do";
	} else if (jqXHR.status == '0') {
		console.log("ERR=" + 'HTTP status code: ' + jqXHR.status + '\n' + 'textStatus: ' + status + '\n' + 'errorThrown: ' + error);
		console.log('HTTP message body (jqXHR.responseText): ' + '\n' + jqXHR.responseText);
	}
	else {
		alert("ERR=" + 'HTTP status code: ' + jqXHR.status + '\n' + 'textStatus: ' + status + '\n' + 'errorThrown: ' + error);
		console.log('HTTP message body (jqXHR.responseText): ' + '\n' + jqXHR.responseText);
	}
}

/**
 *컴마 제거
 */
function removeComma(str) {
	var n = "";
	if (!isEmpty(str)) {
		n = parseInt(str.replace(/,/g, ""));
	}

	return n;
}

/* excel cell format 삭제 */
function exportData_noFormat(me, _noFmtObj) {
	var _formatBak = {};

	for (var key in _noFmtObj) {
		var column = me.getColumn({
			dataIndx: key
		});

		_formatBak[key] = column.format;
		column.format = _noFmtObj[key];
	}

	return _formatBak;
}


/* form 필수 여부 확인 */
function _checkFormValid(form) {

	if (form == undefined || form == null) {
		return true;
	}

	for (var i = 0; i < form.length; i++) {
		if (!isEmpty(form.elements[i].title) && !isEmpty($(form.elements[i]).attr("minlength"))) {

			//#1 필수 입력 체크
			if (fnChkByte4PqGrid(form.elements[i].value) < 1) {
				alert(form.elements[i].title + "은(는) 필수 입력입니다.");
				form.elements[i].focus();
				return false;
			}

			//#2 길이 체크
			if (!isEmpty(form.elements[i].maxLength) && form.elements[i].maxLength != -1) {
				if (fnChkByte4PqGrid(form.elements[i].value.trim()) > form.elements[i].maxLength) {
					alert(form.elements[i].title + "은(는)" + form.elements[i].maxLength + "byte 까지 입력가능합니다.(" + fnChkByte4PqGrid(form.elements[i].value) + "/" + form.elements[i].maxLength + ")");
					form.elements[i].focus();
					return false;
				}
			}
		}
	}
	return true;
}

//이메일 형식 체크
function validateEmail(sEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}

//비밀번호 형식 체크
function validatePswd(sPswd) {
	var filter = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
	if (filter.test(sPswd)) {
		return true;
	} else {
		return false;
	}
}


//우편번호 팝업
function fnAddres(post1, addr1, addr2) {
	//카카오 지도 발생
	new daum.Postcode({
		oncomplete: function(data) { //선택시 입력값 세팅
			debugger;
			//우편번호
			document.getElementById(post1).value = data.zonecode;
			//기본주소
			document.getElementById(addr1).value = data.address; // 주소 넣기
			//상세주소
			document.querySelector("input[id=" + addr2 + "]").focus(); //상세입력 포커싱
		}
	}).open();
}


function getParameterByName(name) { 
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]"); 
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search); 
		return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " ")); 
	}

