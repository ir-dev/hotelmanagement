(function(e){function t(t){for(var o,c,i=t[0],l=t[1],m=t[2],f=0,u=[];f<i.length;f++)c=i[f],Object.prototype.hasOwnProperty.call(a,c)&&a[c]&&u.push(a[c][0]),a[c]=0;for(o in l)Object.prototype.hasOwnProperty.call(l,o)&&(e[o]=l[o]);s&&s(t);while(u.length)u.shift()();return n.push.apply(n,m||[]),r()}function r(){for(var e,t=0;t<n.length;t++){for(var r=n[t],o=!0,i=1;i<r.length;i++){var l=r[i];0!==a[l]&&(o=!1)}o&&(n.splice(t--,1),e=c(c.s=r[0]))}return e}var o={},a={index:0},n=[];function c(t){if(o[t])return o[t].exports;var r=o[t]={i:t,l:!1,exports:{}};return e[t].call(r.exports,r,r.exports,c),r.l=!0,r.exports}c.m=e,c.c=o,c.d=function(e,t,r){c.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:r})},c.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},c.t=function(e,t){if(1&t&&(e=c(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var r=Object.create(null);if(c.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)c.d(r,o,function(t){return e[t]}.bind(null,o));return r},c.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return c.d(t,"a",t),t},c.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},c.p="/spa/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],l=i.push.bind(i);i.push=t,i=i.slice();for(var m=0;m<i.length;m++)t(i[m]);var s=l;n.push([0,"chunk-vendors"]),r()})({0:function(e,t,r){e.exports=r("56d7")},"328a":function(e,t,r){"use strict";r("34eb")},"34eb":function(e,t,r){},3535:function(e,t,r){"use strict";r("5321")},"39e9":function(e,t,r){},5321:function(e,t,r){},"56d7":function(e,t,r){"use strict";r.r(t);r("e260"),r("e6cf"),r("cca6"),r("a79d");var o=r("7a23"),a=(r("b0c0"),{class:"tm-main-content",id:"top"}),n={key:0,class:"alert alert-danger",role:"alert"},c={class:"tm-bg-white ie-container-width-fix-2"},i={class:"container ie-h-align-center-fix"},l={class:"row"},m={class:"col-xs-12 ml-auto mr-auto ie-container-width-fix"},s=Object(o["f"])("div",{class:"card-header",style:{background:"#ee4c52"},id:"tm-section-2"},[Object(o["f"])("h5",null,"Categories")],-1),f=Object(o["f"])("div",{class:"card-footer"},[Object(o["f"])("small",{class:"text-muted"},"Last updated 3 mins ago")],-1),u=Object(o["f"])("input",{type:"reset",class:"btn"},null,-1),d=Object(o["f"])("input",{type:"submit",class:"btn"},null,-1);function p(e,t,r,p,b,O){var h=Object(o["k"])("Navigationbar"),j=Object(o["k"])("StayDetails"),g=Object(o["k"])("RoomAssignment"),v=Object(o["k"])("GuestDetails"),y=Object(o["k"])("PaymentDetails");return Object(o["i"])(),Object(o["e"])("div",a,[Object(o["h"])(h),b.errormessage?(Object(o["i"])(),Object(o["e"])("div",n,[Object(o["f"])("h1",null,Object(o["l"])(this.errormessage),1)])):Object(o["d"])("",!0),Object(o["f"])("div",c,[Object(o["f"])("div",i,[Object(o["f"])("div",l,[Object(o["f"])("div",m,[Object(o["f"])("form",{onSubmit:t[0]||(t[0]=Object(o["q"])((function(e){return O.submitForm()}),["prevent"])),method:"post",id:"bookingForm",class:"tm-search-form tm-section-pad-2"},[Object(o["h"])(j,{formProp:b.form,onUpdateForm:O.updateFormStayDetails,onGetCategories:O.getCategories},null,8,["formProp","onUpdateForm","onGetCategories"]),s,(Object(o["i"])(!0),Object(o["e"])(o["a"],null,Object(o["j"])(b.categories,(function(e){return Object(o["i"])(),Object(o["c"])(g,{key:e.name,category:e,onSelectedCategories:O.updateSelectedCategories},null,8,["category","onSelectedCategories"])})),128)),f,Object(o["h"])(v,{formProp:b.form,onUpdateForm:O.updateFormGuestDetails},null,8,["formProp","onUpdateForm"]),Object(o["h"])(y,{formProp:b.form,onUpdateForm:O.updateFormPaymentDetails},null,8,["formProp","onUpdateForm"]),u,d],32)])])])])])}var b=r("eded"),O=r.n(b),h={class:"tm-top-bar",id:"tm-top-bar"},j=Object(o["g"])('<div class="container"><div class="row"><nav class="navbar navbar-expand-lg navbar-light"><div class="col"><img src="'+O.a+'" height="100px"></div><div class="col"><h1>Hotel Schwarz</h1></div><div id="mainNav" class="collapse navbar-collapse tm-bg-white"><ul class="navbar-nav ml-auto"><li class="nav-item"><a class="nav-link" href="#tm-section-1">StayDetails</a></li><li class="nav-item"><a class="nav-link" href="#tm-section-2">RoomAssignment</a></li><li class="nav-item"><a class="nav-link" href="#tm-section-3">GuestDetails</a></li><li class="nav-item"><a class="nav-link" href="#tm-section-4">PaymentDetails</a></li></ul></div></nav></div></div>',1),g=[j];function v(e,t,r,a,n,c){return Object(o["i"])(),Object(o["e"])("div",h,g)}var y={name:"Navigationbar"},C=(r("3535"),r("6b0d")),F=r.n(C);const N=F()(y,[["render",v]]);var x=N,D={class:"tm-section tm-section-pad tm-bg-gray",id:"tm-section-1"},P={class:"form-row tm-search-form-row"},k={class:"form-group tm-form-element"},w=Object(o["f"])("i",{class:"fa fa-calendar fa-2x tm-form-element-icon"},null,-1),V={class:"form-group tm-form-element"},z=Object(o["f"])("i",{class:"fa fa-calendar fa-2x tm-form-element-icon"},null,-1),U={class:"form-group tm-form-element"},T=Object(o["f"])("i",{class:"fa fa-calendar fa-2x tm-form-element-icon"},null,-1),S={class:"form-group tm-form-element"},R=Object(o["f"])("i",{class:"fa fa-2x fa-user tm-form-element-icon"},null,-1);function B(e,t,r,a,n,c){return Object(o["i"])(),Object(o["e"])("div",D,[Object(o["f"])("div",P,[Object(o["f"])("div",k,[w,Object(o["p"])(Object(o["f"])("input",{name:"arrivalDate","onUpdate:modelValue":t[0]||(t[0]=function(e){return n.form.arrivalDate=e}),onChange:t[1]||(t[1]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"date",class:"form-control",id:"arrivalDate"},null,544),[[o["o"],n.form.arrivalDate]])]),Object(o["f"])("div",V,[z,Object(o["p"])(Object(o["f"])("input",{name:"departureDate","onUpdate:modelValue":t[2]||(t[2]=function(e){return n.form.departureDate=e}),onChange:t[3]||(t[3]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"date",class:"form-control",id:"departureDate"},null,544),[[o["o"],n.form.departureDate]])]),Object(o["f"])("div",U,[T,Object(o["p"])(Object(o["f"])("input",{name:"arrivalTime","onUpdate:modelValue":t[4]||(t[4]=function(e){return n.form.arrivalTime=e}),onChange:t[5]||(t[5]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"time",class:"form-control",id:"arrivalTime"},null,544),[[o["o"],n.form.arrivalTime]])]),Object(o["f"])("div",S,[R,Object(o["p"])(Object(o["f"])("input",{type:"number","onUpdate:modelValue":t[6]||(t[6]=function(e){return n.form.numberOfPersons=e}),onChange:t[7]||(t[7]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),id:"numberOfPersons",class:"form-control",min:"1",placeholder:"2"},null,544),[[o["o"],n.form.numberOfPersons]])])])])}var H={name:"StayDetails",props:{formProp:{type:Object,required:!0}},data:function(){return{form:this.copyFormProp()}},methods:{updateForm:function(){this.$emit("update-form",this.form),this.$emit("get-categories",this.form)},copyFormProp:function(){var e={};return Object.assign(e,this.formProp),e}}};const M=F()(H,[["render",B]]);var A=M,G=(r("a4d3"),r("e01a"),{class:"card"}),I={class:"card-horizontal"},E={class:"img-square-wrapper"},q=["src"],$={style:{width:"18rem",float:"left"}},_={class:"card-body"},J={class:"card-title"},L={class:"card-text"},Z={class:"d-inline p-2 bg-primary text-white"},K={style:{width:"25rem",float:"right"}},Q={class:"card-body"},W=Object(o["f"])("h4",null,"Selected Rooms",-1),X={class:"center"},Y=["value"];function ee(e,t,r,a,n,c){return Object(o["i"])(),Object(o["e"])("div",G,[Object(o["f"])("div",I,[Object(o["f"])("div",E,[Object(o["f"])("img",{class:"img",src:r.category.imageUrl,alt:"Card image cap"},null,8,q)]),Object(o["f"])("div",$,[Object(o["f"])("div",_,[Object(o["f"])("h5",J,Object(o["l"])(this.category.name),1),Object(o["f"])("p",L,Object(o["l"])(this.category.description),1),Object(o["f"])("div",Z,Object(o["l"])(this.category.price.amount)+" "+Object(o["l"])(this.category.price.currency.code)+" Per Night",1)])]),Object(o["f"])("div",K,[Object(o["f"])("div",Q,[W,Object(o["f"])("table",X,[Object(o["f"])("tr",null,[Object(o["f"])("td",null,[Object(o["f"])("button",{type:"button",class:"btn btn-outline-danger",onClick:t[0]||(t[0]=function(){return c.decrementCounter&&c.decrementCounter.apply(c,arguments)})},"-")]),Object(o["f"])("td",null,[Object(o["f"])("input",{type:"number",style:{width:"50px",height:"30px"},value:n.roomCount},null,8,Y)]),Object(o["f"])("td",null,[Object(o["f"])("button",{type:"button",class:"btn btn-outline-success",onClick:t[1]||(t[1]=function(){return c.incrementCounter&&c.incrementCounter.apply(c,arguments)})},"+")])])])])])])])}var te={name:"RoomAssignment",data:function(){return{roomCount:0}},props:{category:{type:Object,required:!0}},methods:{incrementCounter:function(){this.roomCount<15&&(this.roomCount+=1,this.$emit("selected-categories",this.category.name,this.roomCount))},decrementCounter:function(){this.roomCount>0&&(this.roomCount-=1,this.$emit("selected-categories",this.category.name,this.roomCount))}}};r("9227");const re=F()(te,[["render",ee]]);var oe=re,ae={class:"tm-section tm-section-pad tm-bg-gray",id:"tm-section-3"},ne={class:"form-row tm-search-form-row"},ce={class:"form-check"},ie=Object(o["f"])("label",{class:"form-check-label",for:"flexCheckDefault"}," Organization ",-1),le={class:"form-group tm-form-element tm-form-element-50"},me=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),se={class:"form-group tm-form-element tm-form-element-50"},fe=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),ue={class:"form-row tm-search-form-row"},de={class:"form-group tm-form-element tm-form-element-50"},pe=Object(o["f"])("option",{value:""},"Salutation",-1),be=Object(o["f"])("option",{value:"MR"},"Mr",-1),Oe=Object(o["f"])("option",{value:"MS"},"Ms",-1),he=[pe,be,Oe],je=Object(o["f"])("i",{class:"fa fa-2x fa-user tm-form-element-icon"},null,-1),ge={class:"form-group tm-form-element tm-form-element-50"},ve=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),ye={class:"form-group tm-form-element tm-form-element-50"},Ce=Object(o["f"])("i",{class:"fa fa-calendar fa-2x tm-form-element-icon"},null,-1),Fe={class:"form-row tm-search-form-row"},Ne={class:"form-group tm-form-element tm-form-element-50"},xe=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),De={class:"form-group tm-form-element tm-form-element-50"},Pe=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),ke={class:"form-group tm-form-element tm-form-element-50"},we=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),Ve={class:"form-row tm-search-form-row"},ze={class:"form-group tm-form-element tm-form-element-50"},Ue=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1),Te={class:"form-group tm-form-element tm-form-element-50"},Se=Object(o["f"])("option",{value:"AT"},"Austria",-1),Re=Object(o["f"])("option",{value:"DE"},"Germany",-1),Be=Object(o["f"])("option",{value:"CH"},"Switzerland",-1),He=[Se,Re,Be],Me=Object(o["f"])("i",{class:"fa fa-2x fa-user tm-form-element-icon"},null,-1),Ae={class:"form-group tm-form-element tm-form-element-50"},Ge=Object(o["f"])("i",{class:"fa fa-map-marker fa-2x tm-form-element-icon"},null,-1);function Ie(e,t,r,a,n,c){return Object(o["i"])(),Object(o["e"])("div",ae,[Object(o["f"])("div",ne,[Object(o["f"])("div",ce,[Object(o["p"])(Object(o["f"])("input",{class:"form-check-input","onUpdate:modelValue":t[0]||(t[0]=function(e){return n.form.isOrganization=e}),onClick:t[1]||(t[1]=function(e){return c.toggle()}),onChange:t[2]||(t[2]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"checkbox",value:"true",id:"flexCheckDefault"},null,544),[[o["m"],n.form.isOrganization]]),ie]),Object(o["f"])("div",le,[me,Object(o["p"])(Object(o["f"])("input",{name:"organizationName","onUpdate:modelValue":t[3]||(t[3]=function(e){return n.form.organizationName=e}),onChange:t[4]||(t[4]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"organizationName",placeholder:"",disabled:""},null,544),[[o["o"],n.form.organizationName]])]),Object(o["f"])("div",se,[fe,Object(o["p"])(Object(o["f"])("input",{name:"discountRate","onUpdate:modelValue":t[5]||(t[5]=function(e){return n.form.discountRate=e}),onChange:t[6]||(t[6]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"discountRate",placeholder:"",disabled:""},null,544),[[o["o"],n.form.discountRate]])])]),Object(o["f"])("div",ue,[Object(o["f"])("div",de,[Object(o["p"])(Object(o["f"])("select",{"onUpdate:modelValue":t[7]||(t[7]=function(e){return n.form.salutation=e}),onChange:t[8]||(t[8]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),class:"form-control tm-select",id:"salutation"},he,544),[[o["n"],n.form.salutation]]),je]),Object(o["f"])("div",ge,[ve,Object(o["p"])(Object(o["f"])("input",{name:"firstName","onUpdate:modelValue":t[9]||(t[9]=function(e){return n.form.firstName=e}),onChange:t[10]||(t[10]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"firstName",placeholder:"Firstname"},null,544),[[o["o"],n.form.firstName]])]),Object(o["f"])("div",ye,[Ce,Object(o["p"])(Object(o["f"])("input",{name:"lastName","onUpdate:modelValue":t[11]||(t[11]=function(e){return n.form.lastName=e}),onChange:t[12]||(t[12]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"lastName",placeholder:"Lastname"},null,544),[[o["o"],n.form.lastName]])])]),Object(o["f"])("div",Fe,[Object(o["f"])("div",Ne,[xe,Object(o["p"])(Object(o["f"])("input",{name:"dateOfBirth","onUpdate:modelValue":t[13]||(t[13]=function(e){return n.form.dateOfBirth=e}),onChange:t[14]||(t[14]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"date",class:"form-control",id:"dateOfBirth",placeholder:"Date Of Birth"},null,544),[[o["o"],n.form.dateOfBirth]])]),Object(o["f"])("div",De,[Pe,Object(o["p"])(Object(o["f"])("input",{name:"street","onUpdate:modelValue":t[15]||(t[15]=function(e){return n.form.street=e}),onChange:t[16]||(t[16]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"street",placeholder:"Street"},null,544),[[o["o"],n.form.street]])]),Object(o["f"])("div",ke,[we,Object(o["p"])(Object(o["f"])("input",{name:"zipcode","onUpdate:modelValue":t[17]||(t[17]=function(e){return n.form.zipcode=e}),onChange:t[18]||(t[18]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"zipcode",placeholder:"Zipcode"},null,544),[[o["o"],n.form.zipcode]])])]),Object(o["f"])("div",Ve,[Object(o["f"])("div",ze,[Ue,Object(o["p"])(Object(o["f"])("input",{name:"city","onUpdate:modelValue":t[19]||(t[19]=function(e){return n.form.city=e}),onChange:t[20]||(t[20]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"city",placeholder:"City"},null,544),[[o["o"],n.form.city]])]),Object(o["f"])("div",Te,[Object(o["p"])(Object(o["f"])("select",{"onUpdate:modelValue":t[21]||(t[21]=function(e){return n.form.country=e}),onChange:t[22]||(t[22]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),class:"form-control tm-select",id:"country"},He,544),[[o["n"],n.form.country]]),Me]),Object(o["f"])("div",Ae,[Ge,Object(o["p"])(Object(o["f"])("input",{name:"zipcode","onUpdate:modelValue":t[23]||(t[23]=function(e){return n.form.specialNotes=e}),onChange:t[24]||(t[24]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),type:"text",class:"form-control",id:"specialNotes",placeholder:"Special Notes"},null,544),[[o["o"],n.form.specialNotes]])])])])}var Ee={name:"GuestDetails",props:{formProp:{type:Object,required:!0}},data:function(){return{form:this.copyFormProp()}},methods:{updateForm:function(){this.$emit("update-form",this.form)},toggle:function(){var e=document.getElementById("organizationName"),t=document.getElementById("discountRate"),r=document.getElementById("flexCheckDefault");r.checked?(e.disabled=!1,e.placeholder="Organization Name",t.disabled=!1,t.placeholder="Discount Rate"):(e.disabled=!0,e.placeholder="",this.form.organizationName="",t.disabled=!0,t.placeholder="",this.form.discountRate="")},copyFormProp:function(){var e={};return Object.assign(e,this.formProp),e}}};const qe=F()(Ee,[["render",Ie]]);var $e=qe,_e={class:"tm-section tm-section-pad tm-bg-gray",id:"tm-section-4"},Je={class:"form-row tm-search-form-row"},Le={class:"form-group tm-form-element"},Ze=Object(o["f"])("label",{for:"cardHolderName",class:"form-label"},"Card Holder Name",-1),Ke={class:"form-group tm-form-element"},Qe=Object(o["f"])("label",{for:"cardNumber",class:"form-label"},"Card Number",-1),We={class:"form-group tm-form-element"},Xe=Object(o["f"])("label",{for:"cardValidThru",class:"form-label"},"Valid thru",-1),Ye={class:"form-group tm-form-element"},et=Object(o["f"])("label",{for:"cardCvc",class:"form-label"},"CVC",-1),tt={class:"form-group tm-form-element"},rt=Object(o["f"])("label",{class:"form-label"},"PaymentType",-1),ot=Object(o["f"])("option",{value:"CREDITCARD"},"Credit Card",-1),at=Object(o["f"])("option",{value:"CASH"},"Cash",-1),nt=Object(o["f"])("option",{value:"INVOICE"},"Invoice",-1),ct=[ot,at,nt];function it(e,t,r,a,n,c){return Object(o["i"])(),Object(o["e"])("div",_e,[Object(o["f"])("div",Je,[Object(o["f"])("div",Le,[Ze,Object(o["p"])(Object(o["f"])("input",{type:"text","onUpdate:modelValue":t[0]||(t[0]=function(e){return n.form.cardHolderName=e}),onChange:t[1]||(t[1]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),id:"cardHolderName",class:"form-control",placeholder:"Max Mustermann"},null,544),[[o["o"],n.form.cardHolderName]])]),Object(o["f"])("div",Ke,[Qe,Object(o["p"])(Object(o["f"])("input",{type:"text","onUpdate:modelValue":t[2]||(t[2]=function(e){return n.form.cardNumber=e}),onChange:t[3]||(t[3]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),id:"cardNumber",class:"form-control",placeholder:"#### #### #### #### ####"},null,544),[[o["o"],n.form.cardNumber]])]),Object(o["f"])("div",We,[Xe,Object(o["p"])(Object(o["f"])("input",{type:"text","onUpdate:modelValue":t[4]||(t[4]=function(e){return n.form.cardValidThru=e}),onChange:t[5]||(t[5]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),id:"cardValidThru",class:"form-control",placeholder:"12/24"},null,544),[[o["o"],n.form.cardValidThru]])]),Object(o["f"])("div",Ye,[et,Object(o["p"])(Object(o["f"])("input",{type:"text","onUpdate:modelValue":t[6]||(t[6]=function(e){return n.form.cardCvc=e}),onChange:t[7]||(t[7]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),id:"cardCvc",class:"form-control",placeholder:"###"},null,544),[[o["o"],n.form.cardCvc]])]),Object(o["f"])("div",tt,[rt,Object(o["p"])(Object(o["f"])("select",{"onUpdate:modelValue":t[8]||(t[8]=function(e){return n.form.paymentType=e}),onChange:t[9]||(t[9]=function(){return c.updateForm&&c.updateForm.apply(c,arguments)}),class:"form-control",id:"paymentType"},ct,544),[[o["n"],n.form.paymentType]])])])])}var lt={name:"PaymentDetails",props:{formProp:{type:Object,required:!0}},data:function(){return{form:this.copyFormProp()}},methods:{updateForm:function(){this.$emit("update-form",this.form)},copyFormProp:function(){var e={};return Object.assign(e,this.formProp),e}}};const mt=F()(lt,[["render",it]]);var st=mt,ft=r("bc3a"),ut=r.n(ft),dt=r("130e");Object(o["b"])().use(dt["a"],ut.a);var pt={name:"App",components:{Navigationbar:x,StayDetails:A,RoomAssignment:oe,GuestDetails:$e,PaymentDetails:st},data:function(){return{form:{arrivalDate:"",departureDate:"",arrivalTime:"",numberOfPersons:"",selectedCategoriesRoomCount:{},isOrganization:!1,organizationName:"",discountRate:"",salutation:"",firstName:"",lastName:"",dateOfBirth:"",street:"",zipcode:"",city:"",country:"",specialNotes:"",cardHolderName:"",cardNumber:"",cardValidThru:"",cardCvc:"",paymentType:""},categories:null,errormessage:""}},methods:{getCategories:function(){var e=this;this.form.arrivalDate&&this.form.departureDate&&ut.a.get("http://127.0.0.1:8080/rest/categories?arrivalDate="+this.form.arrivalDate+"&departureDate="+this.form.departureDate).then((function(t){console.log(t.data),e.categories=t.data,e.errormessage=t.data.message}),(function(t){console.log(t),e.errormessage=t.message}))},submitForm:function(){var e=this;ut.a.post("http://127.0.0.1:8080/rest/bookings/create",this.form).then((function(t){console.log(t.status),document.getElementById("bookingForm").reset(),e.errormessage=t.data.message}),(function(t){console.log(t),e.errormessage=t.message}))},updateFormStayDetails:function(e){this.form.arrivalDate=e.arrivalDate,this.form.departureDate=e.departureDate,this.form.arrivalTime=e.arrivalTime,this.form.numberOfPersons=e.numberOfPersons},updateFormGuestDetails:function(e){this.form.isOrganization=e.isOrganization,this.form.organizationName=e.organizationName,this.form.discountRate=e.discountRate,this.form.salutation=e.salutation,this.form.firstName=e.firstName,this.form.lastName=e.lastName,this.form.dateOfBirth=e.dateOfBirth,this.form.street=e.street,this.form.zipcode=e.zipcode,this.form.city=e.city,this.form.country=e.country,this.form.specialNotes=e.specialNotes},updateFormPaymentDetails:function(e){this.form.cardHolderName=e.cardHolderName,this.form.cardNumber=e.cardNumber,this.form.cardValidThru=e.cardValidThru,this.form.cardCvc=e.cardCvc,this.form.paymentType=e.paymentType},updateSelectedCategories:function(e,t){this.form.selectedCategoriesRoomCount[e]=t}}};r("328a");const bt=F()(pt,[["render",p]]);var Ot=bt;Object(o["b"])(Ot).mount("#app")},9227:function(e,t,r){"use strict";r("39e9")},eded:function(e,t,r){e.exports=r.p+"img/hotelschwarz.4cd1fcb1.png"}});
//# sourceMappingURL=index.a9a6b5ea.js.map