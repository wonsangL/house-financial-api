# Kakaopay 사전과제: 주택 금융 서비스 API
### 주택금융 공급현황 분석 서비스
년도별 각 금융기관(은행)에서 신용보증한 금액에 대한 데이터를 활용하는 API 구현

### Backend Stack
- Java 8
- Spring 5.1.8
- Spring Boot 2.1.6
- JPA, Hibernate
- MySQL
- Lombok

## 빌드 및 실행 방법
```bash
#git
git clone https://github.com/wonsangL/house-financial-api.git

#Using Intellij
1. Sync gradle
2. annotation processing 활성화
3. application.properties database information(ID, PWD) 설정
4. Run application
```

## API 정보
| Action | API | request body | response | 
|--------|-----|-----------|------------------|
| CSV 데이터 database에 저장 | [POST]<br>/ | - | - |
| 주택금융 공급 금융기관 목록을 출력 | [GET]<br>/institutes | - | [ <br>&nbsp;&nbsp;&nbsp;"주택도시기금",<br>&nbsp;&nbsp;&nbsp;"국민은행",<br>&nbsp;&nbsp;&nbsp;"우리은행",<br>&nbsp;&nbsp;&nbsp;"신한은행",<br>&nbsp;&nbsp;&nbsp;"한국시티은행",<br>&nbsp;&nbsp;&nbsp;"하나은행",<br>&nbsp;&nbsp;&nbsp;"농협은행/수협은행",<br>&nbsp;&nbsp;&nbsp;"외환은행",<br>&nbsp;&nbsp;&nbsp;"기타은행"<br>]|
| 년도별 각 금융기관의 지원금액 합계를 출력 | [GET]<br>/annual | - | {<br>&nbsp;&nbsp;&nbsp;"name": "주택금융 공급현황",<br>&nbsp;&nbsp;&nbsp;"annualDetails": [<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"year": "2005년",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"totalAmount": 48016,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"detailAmount": {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"하나은행": 3122,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"농협은행/수협은행": 1486,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"우리은행": 2303,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"국민은행": 13231,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"신한은행": 1815,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"주택도시기금": 22247,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"외환은행": 1732,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"한국시티은행": 704,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"기타은행": 1376<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;]<br>} |
| 각 년도별 각 기관의 전체 지원금액 중<br> 가장 큰 금액의 기관명을 출력 | [GET]<br>/max | - | {<br>&nbsp;&nbsp;&nbsp;"maxAmount": [<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"year": 2014,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"bank": "주택도시기금"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;]<br>} |
| 전체 년도에서 외환은행의 지원금액 평균 중<br> 가장 작은 금액과 큰 금액을 출력 | [GET]<br>/avg | - | {<br>&nbsp;&nbsp;&nbsp;"bank": "외환은행",<br>&nbsp;&nbsp;&nbsp;"supportMinAmount": [<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"year": 2017,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"amount": 0<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;],<br>&nbsp;&nbsp;&nbsp;"supportMaxAmount": [<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"year": 2015,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"amount": 1702<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;]<br>} |
| 특정 은행의 특정 달에 대해서<br>2018 년도 금융지원 금액을 예측 | [GET]<br>/predict | {<br>&nbsp;&nbsp;&nbsp;"bank":"국민은행",<br>&nbsp;&nbsp;&nbsp;"month": 2<br>} | {<br>&nbsp;&nbsp;&nbsp;"bank": "bank00",<br>&nbsp;&nbsp;&nbsp;"year": 2018,<br>&nbsp;&nbsp;&nbsp;"month": 2,<br>&nbsp;&nbsp;&nbsp;"amount": 2301<br>} |

## 문제해결
### 금융지원 금액을 예측 모델
#### Gradient descent를 활용한 Linear regression
데이터 예측을 위해 아래와 같은 hypothesis, h를 정의

<p align="center">
    <img src="https://i.imgur.com/bhiJfPv.png" width="300"/>
</p>

```java
private double hypothesis(long x) {
        return theta0 * x + theta1;
}
```

위 hypothesis의 cost function 정의

<p align="center">
    <img src="https://i.imgur.com/CPRniGa.png" width="500"/>
</p>

```java
private double cost(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += Math.pow(hypothesis(dataSetX.get(i)) - dataSetY.get(i), 2);
        }

        return sum / (dataSetX.size() * 2);
}
```

Gradient descent를 위해,
cost function을 theta0, theta1에 대하여 각각 미분   

<p align="center">
    <img src="https://i.imgur.com/tsxdzRH.png" width="500"/>
</p>

```java
private double deriveTheta0(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += (hypothesis(dataSetX.get(i)) - dataSetY.get(i)) * dataSetX.get(i);
        }

        return sum / dataSetX.size();
}
```

<p align="center">
    <img src="https://i.imgur.com/byRsTqx.png" width="500"/>
</p>

```java
private double deriveTheta1(){
        double sum = 0;

        for(int i = 0; i < dataSetX.size(); i++){
            sum += (hypothesis(dataSetX.get(i)) - dataSetY.get(i));
        }

        return sum / dataSetX.size();
    }
```

Gradient descent 실행 

```java

public long execute(int year, int month){
        do{
            double temp0 = theta0 - LEARNING_LATE * deriveTheta0();
            double temp1 = theta1 - LEARNING_LATE * deriveTheta1();

            theta0 = temp0; theta1 = temp1;

            iter++;

            if(iter > MAX_ITER){
                break;
            }
        } while(cost() > ALLOWANCE);
}
```

### Csv 파일 저장
#### Requirement
<u>**데이터 파일**</u>에서 각 레코드를 데이터베이스에 저장하는 <u>**API 개발**</u>

#### Problem
파일을 읽고 쓰는 기능을 API로 구현할 경우

API를 호출할 때마다 동일한 기능을 반복,

이미 데이터가 저장된 이후라면 불필요한 동작이 반복

또한 도중에 에러가 발생할 경우 CSV를 다시 처음부터 읽어야하는 문제점이 있음

#### Solution
Spring Batch를 활용하여 해당 기능을 구현

(하지만 문서에는 API 구현이 명시되어있기 때문에 Batch와 API, 모두 구현)

