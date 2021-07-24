function onScroll() {
    const nav = document.querySelector('header');          //헤더
    const goingTop = document.querySelector('#goingTop');  //최상단으로 가는 버튼
    const standard = 500;                                   //기준값

    if (window.pageYOffset > standard) {      //스크롤이 30보다 클 경우 (더 밑에 있을 경우)
        goingTop.classList.remove('blind');   //버튼을 안 보이게 만들던 blind 클래스가 삭제돼 버튼이 나타나고
        nav.classList.add('bg');                //헤더에 bg 클래스를 추가하여 배경색이 나타남
    }else {                                   //스크롤이 30보다 작은 경우 (더 위에 있는 경우)
        goingTop.classList.add('blind');      //버튼을 안 보이게 만드는 blind 클래스가 추가되어 버튼이 사라지고
        nav.classList.remove('bg');       //헤더의 bg 클래스가 삭제돼 배경색이 없어짐
    }
}

window.addEventListener('scroll', onScroll);  //스크롤 하면 onScroll 함수가 실행됨