
document.getElementsByTagName("html")[0].style.fontSize = document.documentElement.clientWidth/10 + "px";
		
		$(".left").on("tap",function(e) {
			e.preventDefault();
			var year = $(".year").text();
			var mouth = $(".mouth").text();
			mouth--;
			if(mouth < 1) {
				mouth = 12;
				year --;
			}
			var param = year.toString()+(mouth<10 ? "0" + mouth : mouth).toString();
			if(eval(initperiod)<=eval(param)){
				$(".year").text(year);
				$(".mouth").text(mouth<10 ? "0" + mouth : mouth);	
				if($("#aRept").hasClass("active")){
					listRpt(param);
				}
				if($("#aTaxation").hasClass("active")){
					listTaxation(param);
				}
				if($("#aWage").hasClass("active")){
					listWageSocialSecurity(param);
				}
			}
			
		});
		$(".right").on("tap",function(e) {
			e.preventDefault();
			var year = $(".year").text();
			var mouth = $(".mouth").text();
			mouth++;
			if(mouth > 12) {
				mouth = 1;
				year ++;
			}
			var param = year.toString()+(mouth<10 ? "0" + mouth : mouth).toString();
			if(eval(param)<=eval(defaultPeriod)){
				$(".year").text(year);
				$(".mouth").text(mouth<10 ? "0" + mouth : mouth);

				if($("#aRept").hasClass("active")){
					listRpt(param);
				}
				if($("#aTaxation").hasClass("active")){
					listTaxation(param);
				}
				if($("#aWage").hasClass("active")){
					listWageSocialSecurity(param);
				}	
			}
		});