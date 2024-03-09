package com.dmk.melodify.domain.member.repository;

import com.dmk.melodify.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
