package net.chinahrd.getSQL;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;

import net.chinahrd.utils.SqlHelper;

public class SqlHelperTest {
	@Ignore
	@Test
	public void getSqlTest() {
//		SqlSession sqlSession = DynamicHelper.getSqlSession();
//		try {
//			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
//
//			System.out.println(SqlHelper.getNamespaceSql(sqlSession,
//					"com.github.pagehelper.mapper.CountryMapper.selectIf2ListAndOrder"));
//
//			System.out.println(SqlHelper.getMapperSql(countryMapper, "selectIf2ListAndOrder", Arrays.asList(1, 2)));
//
//			System.out.println(
//					SqlHelper.getMapperSql(sqlSession, "com.github.pagehelper.mapper.CountryMapper.selectAll"));
//
//			System.out.println(SqlHelper.getMapperSql(sqlSession,
//					"com.github.pagehelper.mapper.CountryMapper.selectIf2ListAndOrder", Arrays.asList(1, 2),
//					Arrays.asList(3, 4), "id"));
//		} finally {
//			sqlSession.close();
//		}
	}
}
