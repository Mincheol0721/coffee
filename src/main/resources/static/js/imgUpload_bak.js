var htImageInfo = []; // img 파일 정보 저장
var rFilter = /^(image\/bmp|image\/gif|image\/jpg|image\/jpeg|image\/png)$/i;
var rFilter2 = /^(bmp|gif|jpg|jpeg|png)$/i;
var nMaxImageSize = 10*1024*1024; // 한 번에 업로드 할 수 있는 이미지 사이즈

// 이벤트 핸들러
function dragEnter(e) {
    e.stopPropagation();
    e.preventDefault();
}

function dragExit(e) {
    e.stopPropagation();
    e.preventDefault();
}

function dragOver(e) {
    e.stopPropagation();
    e.preventDefault();
}

// @param {Object} e
function drop(e) {
    e.stopPropagation();
    e.preventDefault();
}

const dropzone = document.querySelector('#frm');
const contentDiv = document.querySelector('#contentDiv');
const textarea = document.querySelector('#content');
const formData = new FormData();

document.addEventListener('DOMContentLoaded', () => {
    // 이미지를 끌어와 div영역 안으로 마우스를 올렸을 때
    dropzone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropzone.classList.add('hover');
    });

    // div영역 안에서 마우스를 뺐을 때
    dropzone.addEventListener('dragleave', () => {
        dropzone.classList.remove('hover');
    });

    // div영역 안에서 마우스를 놓았을 때
    dropzone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropzone.classList.remove('hover');

        const files = e.dataTransfer.files;

        Array.from(files).forEach(file => {
            if(file.type.startsWith('image/')){
                if(file.size > nMaxImageSize) {
                    alert(`파일 크기는 ${nMaxImageSize}MB를 초과할 수 없습니다.`);
                    e.preventDefault();
                    return;
                }
                const reader = new FileReader();
                reader.onload = function(e) {
                    const pTag = document.createElement("p");
                    const imgTag = document.createElement("img");
                    imgTag.src=e.target.result;
                    imgTag.style.width='50%';
                    pTag.append(imgTag);
                    contentDiv.appendChild(pTag);
                };
                reader.readAsDataURL(file);
            } else {
                alert('이미지 파일만 업로드 가능합니다.');
            }
        });
    }); // drop 이벤트 핸들러

    document.getElementById("frm").addEventListener('submit', function(e) {
        const content = contentDiv.innerHTML;
        textarea.value = content;
        console.log(textarea.value);
    });

}); // DOMContentLoaded


//document.getElementById("submit").addEventListener('click', function(e) {
//    e.preventDefault();
//
//    const content = contentDiv.innerHTML;
//    formData.append('content', content);
//
//    fetch('/dailyBoard/insertDailyBoard', {
//        method: 'POST',
//        body: formData
//    })
//    .then(response => response.json())
//    .then(data => {
//        console.log('서버 응답: ', data);
//        alert('서버에 HTML이 성공적으로 전송되었습니다.');
//    })
//    .catch(error => console.error('** error: ', error))
//});
