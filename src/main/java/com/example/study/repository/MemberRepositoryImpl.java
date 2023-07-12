package com.example.study.repository;

import com.example.study.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.study.entity.QMember.member;

// QueryDsl용 인터페이스의 구현체는 반드시 끝이 Impl로 끝나야 자동으로 인식되어서
// 원본 인터페이스(MemberRepository) 타입의 객체로도 사용이 가능합니다.
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Member> findByName(String name) {
    return queryFactory
      .selectFrom(member)
      .where(member.userName.eq(name))
      .fetch();
  }

  //////////////////////////////////////////////////////////////////////////

  //Where절에 BooleanExpression을 리턴하는 메서드를 사용합니다.
  //nameEq, ageEq에서는 값이 없다면 null을 리턴하고, 그렇지 않을 경우에는 값을 반환합니다.
  //Where절에서는 null값인 경우에는 조건을 건너 뜁니다.
  public List<Member> findUser(String nameParam, Integer ageParam) {
    return queryFactory
      .selectFrom(member)
      .where(nameEq(nameParam), ageEq(ageParam))
      .fetch();
  }

  private BooleanExpression ageEq(Integer ageParam) {
    return ageParam != null ? member.age.eq(ageParam) : null;
  }

  private BooleanExpression nameEq(String nameParam) {
    return nameParam != null ? member.userName.eq(nameParam) : null;
  }

}
