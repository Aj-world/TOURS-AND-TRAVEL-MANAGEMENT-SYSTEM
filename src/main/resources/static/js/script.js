let menu = document.querySelector('#menu-btn');
let navbar = document.querySelector('.header .navbar');

menu.onclick = () =>{
   menu.classList.toggle('fa-times');
   navbar.classList.toggle('active');
};

window.onscroll = () =>{
   menu.classList.remove('fa-times');
   navbar.classList.remove('active');
};

var swiper = new Swiper(".home-slider", {
   loop:true,
   navigation: {
     nextEl: ".swiper-button-next",
     prevEl: ".swiper-button-prev",
   },
});

var swiper = new Swiper(".reviews-slider", {
   grabCursor:true,
   loop:true,
   autoHeight:true,
   spaceBetween: 20,
   breakpoints: {
      0: {
        slidesPerView: 1,
      },
      700: {
        slidesPerView: 2,
      },
      1000: {
        slidesPerView: 3,
      },
   },
});

/* let loadMoreBtn = document.querySelector('.packages .load-more .btn');
let currentItem = 3;

loadMoreBtn.onclick = () =>{
   let boxes = [...document.querySelectorAll('.packages .box-container .box')];
   for (var i = currentItem; i < currentItem + 3; i++){
      boxes[i].style.display = 'inline-block';
   };
   currentItem += 3;
   if(currentItem >= boxes.length){
      loadMoreBtn.style.display = 'none';
   }
} 
*/

 
let loadMoreBtn = document.querySelector('.packages .load-more .btn');

 
let currentItem = 3;

 
if (loadMoreBtn) {  
   loadMoreBtn.addEventListener('click', () => {
      
      let boxes = [...document.querySelectorAll('.packages .box-container .box')];
   
     
      for (let i = currentItem; i < Math.min(currentItem + 3, boxes.length); i++) {
         boxes[i].style.display = 'inline-block';
      }
   
      
      currentItem += 3;
   
     
      if (currentItem >= boxes.length) {
         loadMoreBtn.style.display = 'none';
      }
   });
}

 

 

/*********************************pament releted code************************************/

const paymentStart = () => {
	console.log("payment started");

	fetch('Packge_process2')
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(jsonData => {
			let amount = jsonData.price;
			 
			$.ajax(
				{
					url: '/createOrder', // It should be "url" instead of "URL"
					data: JSON.stringify({ amount: amount, info: "order info" }),
					contentType: "application/json",
					type: "post",
					dataType: "json",

					success: function(response) {
			
						if (response.status == "created") {
							var options = {
								"key": "rzp_test_Obz1IQlDtOvh1b", // Enter the Key ID generated from the Dashboard
								"amount": response.amount, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
								"currency": "INR",
								"name": "Aj world",
								"description": "Test Transaction",
								"image": "https://as2.ftcdn.net/v2/jpg/03/35/08/79/1000_F_335087931_YPDWx4rfylDAJ6nsQaqE1C6KNN7SNhGm.jpg",
								"order_id": response.id, //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
								"handler": function(response) {
									alert(response.razorpay_payment_id);
									alert(response.razorpay_order_id);
									alert(response.razorpay_signature)
								},
								"prefill": {
									"name": " ",
									"email": " ",
									"contact": " "
								},
								"notes": {
									"address": "Razorpay Corporate Office"
								},
								"theme": {
									"color": "#2a07f0;"
								}
							};
							var rzp1 = new Razorpay(options);
							rzp1.on('payment.failed', function(response) {
								alert(response.error.code);
								alert(response.error.description);
								alert(response.error.source);
								alert(response.error.step);
								alert(response.error.reason);
								alert(response.error.metadata.order_id);
								alert(response.error.metadata.payment_id);
							});

								rzp1.open();
						}
					},
					error: function(error) {
						console.log(error);
						alert("something went wrong");
					}
				}
			);
		})
		.catch(error => {
			console.error('There was a problem with the fetch operation:', error);
		});
};