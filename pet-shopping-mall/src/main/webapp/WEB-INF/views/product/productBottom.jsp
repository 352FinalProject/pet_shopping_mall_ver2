<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <style>
    .btn {
	width:150px;
	height:50px;
	border-radius: 5px;
	border:none;
	}
	.product-bottom {
	display: flex;
	width: 100%;
	position:fixed;
	margin-bottom: 730px;
	background-color: #fff;
	font-size:18px;
	justify-content: center;
	height: 80px;
	border-top: 1px solid #999999;
	}
	.product-bottom div{
	margin-top:10px;
	margin-right:40px;
	}
	.btn2 {
	background: #58ACFA;
	}
    </style>
    <section>
    	<div class="product-bottom">
    		<div>
    			<span>��ǰ�� ������ ��</span>
    			<br />
    			<span>11,000��</span>
    		</div>
    		<div>
    			<img src="${pageContext.request.contextPath}/resources/images/product/pink_heart.png" width="30px""/>
    			<br />
    			<span>1,562</span>
    		</div>
    		<div>
    			<button class="btn btn1">��ٱ���</button>
    			<button class="btn btn2">�����ϱ�</button>
    		</div>
    	</div>
    </section>