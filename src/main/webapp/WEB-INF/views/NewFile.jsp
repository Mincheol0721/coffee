
<div class="recFrm">
	<form action="/coffee/dailyboardComment/regComment" method="post" class="recFrm" style="display: none;">
		<input type="hidden" name="nickname" value="${member.nickname}">
		<input type="hidden" name="boardNo" value="${vo.no}"> 
		<input type="hidden" name="parentNo" value=" parentNo "> 
		<span class="comment" style="display: flex; justify-content: space-between; flex-wrap: nowrap;">
			<span style="padding-top: 0.5rem; margin-bottom: -1.2rem;">
				└&nbsp;
			</span> 
			<input class="input-box" type="text" name="content" value="${list.content}">
			<div style="display: flex; justify-content: flex-end;">
				<button class="button signupBtn" onclick="javascript: modComment(${list.no}, ${vo.no}, ${loop.index})" style="font-size: 12px; margin: auto;">
					<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
				    	<path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12h15m0 0l-6.75-6.75M19.5 12l-6.75 6.75"></path>
				  	</svg>
					<div class="text" style="margin: 0 1.2em;">댓글 작성</div>
				</button>
			</div> 
			<span class="underline"></span>
		</span>
	</form>
</div>