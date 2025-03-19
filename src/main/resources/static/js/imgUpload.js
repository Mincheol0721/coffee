const dropzone = document.querySelector('#dropzone');
const textarea = document.querySelector('#content');
const inputFiles = document.querySelector("#files");
const formData = new FormData();
const fileNameList = new Set();
 // 기존 파일 포함 새로운 파일 추가
const fileSet = new Set();
let i = 1;
let isCleared = false;
//if (dropzone.classList.contains('before')) {
//    dropzone.innerText = '여기에 업로드할 이미지를 드래그 해주세요.';
//}

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
        if (!isCleared) {
            dropzone.innerText = '';
            isCleared = true;
        }
        e.preventDefault();
        dropzone.classList.remove('hover');
        dropzone.classList.remove('before');

        const files = e.dataTransfer.files; // 드롭된 파일들
        const fileInputFiles = inputFiles.files;
        const newFileList = new DataTransfer();

        // 기존 파일 리스트에 새 파일 추가
        for (let k=0; k < fileInputFiles.length; k++) {
            fileNameList.add(fileInputFiles[k].name);
            newFileList.items.add(fileInputFiles[k]);
        }

        Array.from(files).forEach(file => {
            if (fileNameList.size < 10) {
                if(file.type.startsWith('image/')){
                    if(fileNameList.has(file.name)){
                        alert(file.name + "파일이 존재합니다.");
                        return;
                    }
                    // 새 파일 추가
                    newFileList.items.add(file);
                    fileNameList.add(file.name);

                    const pTag = document.createElement('p');
                    pTag.style.margin = '0px';
                    pTag.style.padding = '0px';
                    pTag.append((i++) + '. ' + file.name);
                    dropzone.append(pTag);
                    console.log(pTag.textContent);
                }
            } else {
                alert('파일은 최대 10개까지만 추가 가능합니다.');
                return;
            }
            inputFiles.files = newFileList.files;
        }); // Array.from(files).forEach문
    }); // drop 이벤트 핸들러

}); // DOMContentLoaded