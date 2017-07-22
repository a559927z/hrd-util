package net.chinahrd.utils.db.insert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.ExcelUtil;

public class Excel2Db {

	private String filePath = "Z:\\TEMP\\过程列表.xls";

	private void run() throws IOException {
		List<Map> obj = ExcelUtil.readExcel2007(filePath);

		System.out.println(obj);
	}

	public static void main(String[] args) throws IOException {

		String [] arr ={"pro_fetch_dim_all","pro_fetch_dim_organization","pro_update_dim_organization","pro_fetch_dim_position","pro_fetch_dim_sequence","pro_fetch_dim_sequence_sub","pro_fetch_dim_job_title","pro_fetch_dim_ability","pro_fetch_dim_ability_lv","pro_fetch_dim_key_talent_type","pro_fetch_dim_course","pro_fetch_dim_course_type","pro_fetch_dim_run_off","pro_fetch_dim_structure","pro_fetch_dim_project_type","pro_fetch_dim_project_input_type","pro_fetch_dim_change_type","pro_fetch_dim_channel","pro_fetch_dim_dismission_week","pro_fetch_dim_encourages","pro_fetch_dim_performance","pro_fetch_dim_population","pro_fetch_dim_quality","pro_fetch_dim_checkwork_type","pro_fetch_dim_certificate_info","pro_fetch_dim_sales_team","pro_fetch_dim_sales_product","pro_fetch_dim_separation_risk","pro_fetch_dim_organization_type","pro_fetch_dim_satfac_genre","pro_fetch_dim_dedicat_genre","pro_fetch_dim_profession","pro_fetch_dim_city","pro_fetch_dim_province","pro_fetch_base_all","pro_fetch_v_dim_emp","pro_update_v_dim_emp","pro_update_key_talent","pro_update_emp_report_relation","pro_fetch_underling","pro_fetch_emp_family","pro_fetch_emp_prof_title","pro_fetch_emp_edu","pro_fetch_emp_bonus_penalty","pro_fetch_emp_contact_person","pro_fetch_emp_past_resume","pro_fetch_emp_certificate_info","pro_fetch_population_relation","pro_fetch_organization_emp_relation","pro_fetch_budget_emp_number","pro_fetch_budget_position_number","pro_fetch_dim_emp","pro_fetch_dim_emp_month","pro_fetch_history_dim_organization_month","pro_fetch_history_emp_count","pro_fetch_quota_all","pro_fetch_quota_RenJunXiaoYi","pro_fetch_target_benefit_value","pro_fetch_trade_profit","pro_fetch_fact_fte","pro_update_ffRange","pro_fetch_profession_value","pro_fetch_quota_RenLiChengBen","pro_fetch_manpower_cost_item","pro_fetch_manpower_cost","pro_fetch_manpower_cost_value","pro_fetch_quota_XinChouKanBan","pro_fetch_share_holding","pro_fetch_pay","pro_fetch_pay_collect","pro_update_pay_crValue","pro_fetch_pay_collect_year","pro_fetch_salary","pro_fetch_welfare_nfb","pro_fetch_welfare_cpm","pro_fetch_welfare_uncpm","pro_welfare_nfb_total","pro_welfare_cpm_total","pro_fetch_profession_quantile_relation","pro_recruit_salary_statistics","pro_fetch_welfare_cpm_total","pro_fetch_welfare_nfb_total","pro_fetch_salary_year","pro_fetch_pay_value","pro_fetch_dept_kpi","pro_fetch_all_sales","pro_fecth_sales_emp","pro_fetch_sales_team_target","pro_fetch_sales_pro_target","pro_fetch_sales_org_target","pro_fetch_sales_emp_target","pro_fetch_sales_emp_rank","pro_fetch_sales_team_rank","pro_fetch_sales_ability","pro_fetch_sales_detail","pro_fetch_history_sales_detail","pro_fetch_sales_org_day","pro_fetch_sales_emp_org_pro_month","pro_fetch_organization_change","pro_fetch_sales_emp_month","pro_fetch_sales_org_prod_month","pro_fetch_all_map","pro_fetch_dim_ability_number","pro_fetch_map_talent_info","pro_fetch_map_adjustment","pro_fetch_map_config","pro_fetch_ability_change","pro_fetch_dim_z_info","pro_fetch_map_talent","pro_fetch_job_change","pro_fetch_all_promotion","pro_fetch_promotion_element_scheme","pro_fetch_promotion_plan","pro_fetch_emp_pq_eval_relation","pro_fetch_promotion_total","pro_update_promotion_analysis","pro_update_promotion_total","pro_fetch_promotion_results","pro_fetch_promotion_analysis","pro_fetch_all_checkwork","pro_fetch_permiss_import","pro_fetch_emp_attendance_day","pro_fetch_emp_other_day","pro_fetch_emp_overtime_day","pro_fetch_theory_attendance","pro_fetch_emp_attendance_month","pro_fetch_population_relation_month","pro_fetch_all_recruit","pro_fetch_emp_pq_relation","pro_fetch_position_quality","pro_fetch_matching_soure","pro_fetch_recruit_result","pro_fetch_recruit_public","pro_fetch_recruit_channel","pro_fetch_recruit_value","pro_fetch_recruit_salary_statistics","pro_fetch_out_talent","pro_fetch_recruitment_process","pro_fetch_all_key_talent","pro_fetch_key_talent","pro_fetch_matching_school","pro_fetch_key_talent_tags_auto","pro_fetch_key_talent_encourages","pro_fetch_key_talent_logs","pro_fetch_key_talent_focuses","pro_fetch_key_talent_tags","pro_fetch_key_talent_tags_ed","pro_fetch_all_run_off","pro_fetch_dimission_risk","pro_fetch_dimission_risk_item","pro_fetch_run_off_record","pro_fetch_run_off_total","pro_fetch_all_performance","pro_fetch_performance_change","pro_fetch_all_emp_personality","pro_fetch_emp_personality","pro_fetch_all_project","pro_fetch_projec_cost","pro_fetch_project_input_detail","pro_fetch_project","pro_fetch_project_manpower","pro_fetch_project_partake_relation","pro_fetch_project_maxvalue","pro_fetch_project_cost","pro_fetch_all_train","pro_fetch_lecturer_course_design","pro_fetch_lecturer","pro_fetch_lecturer_course_speak","pro_fetch_course_record","pro_fetch_train_plan","pro_fetch_train_satfac","pro_fetch_train_outlay","pro_fetch_train_value","pro_fetch_all_dedical","pro_fetch_dedicat_organ","pro_fetch_dedicat_genre_soure","pro_fetch_satfac_organ","pro_fetch_satfac_genre_soure","pro_fetch_all_organization_budget","pro_fetch_budget_emp_numbe","pro_fetch_all_emp_profile","pro_fetch_360_report","pro_fetch_360_time","pro_fetch_job_relation"};
		
	Map<String,String> map= CollectionKit.newMap();
		for(String str :arr){
			if(null==map.get(str)){
				map.put(str,"");
			}else{
				System.out.println(str);
			}
		}
		
	
	}
}
