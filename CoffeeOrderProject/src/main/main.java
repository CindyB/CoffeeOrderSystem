package main;

import db.DBConnection;
import singleton.Singleton;
import view.FirstView;

public class main {
	public static void main(String[] args) {

		DBConnection.initConnection();
		Singleton s = Singleton.getInstance();
		s.memCtrl.firstView();
	}
}
/*
	1. 프로젝트 우클릭 -> Build Path -> Comfigure Build Path -> Library에 mysql-connector-java-8.0.22 추가
	2. MySQL Workbench에서 포함된 db로 스키마 및 테이블 생성 후 데이터 삽입
	3. db.DBConnection안에 서버 이름과 비밀번호 바꾸기
	4. 메인에서 실행
 */