jQuery(document).ready(function($) {

	/*
	$('.chart').easyPieChart({
		animate: 1000,
		lineWidth: 20,
		scaleColor: false,
		size: 200
    });
    */
	
    $('input.datepicker').datepicker({
    	format: "dd/mm/yyyy"
    });
    
    window.percentSorter = function(a, b) {
    	a = +a.slice(0, -1);
        b = +b.slice(0, -1);
        if (a > b) return 1;
        if (a < b) return -1;
        return 0;
    };
    
	require(["orion/editor/edit"], function(edit) {
		oeditor = edit({className: "oeditor", expandTab: true, tabSize: 2, lang: 'html'})[0];
		
		if(!oeditor) return;
		
		oeditor.setText($('#script-content').val());
		
		var omodel = oeditor.getModel();
		var oview = oeditor.getTextView();
		
		var onChanged = omodel.onChanged; 
		omodel.onChanged = function(e) {
			onChanged.apply(this, arguments);
			$('#script-content').val(oeditor.getText());
			resize();
		}
		
		var onKeyPress = oview.onKeyPress;
		oview.addEventListener("KeyPress", function(e) {
			if(e.event.ctrlKey && e.event.charCode == 115) {
				e.preventDefault();
				$('#script-content').parents('form').submit();
			}
		});
		
		/*
		var onPaste = oview._handlePaste;
		oview._handlePaste = function() {
			onPaste.apply(this, arguments);
			oeditor.setText(oeditor.getText().replace(/\t/g, '  '));
		}
		*/
		
		function resize() {
			$('#oeditor').height(oview.getModel().getLineCount() * oview.getLineHeight() + 25);
			oview.resize();
		}
		
		resize();
	});
	
	if ($('.glyphicon.glyphicon-refresh').length > 0) {
		setTimeout(function() {
			 location.reload();
		}, 5000);
	}

	if ($('pre.log').length == 1) {
		$(document).scrollTop($(document).height());

		if ($('pre.log').data('status') == 0) {
			setTimeout(function() {
				location.reload();
			}, 1000);
		}
	}

	$(document)
		.on('change', '#script-upload', function(evt) {
			var file = evt.target.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#script-content').val(reader.result).trigger('autosize.resize');
				oeditor.setText($('#script-content').val());
			}
			reader.readAsText(file);
		})
		.on('change', '#execute-all', function() {
			$(this).parents('table').find('input[type="checkbox"]').prop('checked', $(this).prop('checked'));
		})
	;

});