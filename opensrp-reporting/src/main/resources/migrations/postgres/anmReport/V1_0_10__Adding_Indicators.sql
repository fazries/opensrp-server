INSERT INTO anm_report.dim_indicator (indicator) VALUES ('M_LIVE_BIRTH');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('F_LIVE_BIRTH');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_IFA_100');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_IMMUNIZATION_DEATH');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_VIT_A_5_3YR');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_VIT_A_9_3YR');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_DPTBOOSTER_2_5YR');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_HYPERTENSION');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_MMR');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_DPT');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_OPV');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_M_ALL_VACC_2Y');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_F_ALL_VACC_2Y');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('NRHM_ALL_VACC_2Y');
INSERT INTO anm_report.dim_indicator (indicator) VALUES ('ANC_GT_12');
UPDATE anm_report.dim_indicator SET indicator = 'IB_LT_1Y' where indicator = 'IB<1Y';
UPDATE anm_report.dim_indicator SET indicator = 'IB_LT_5Y' where indicator = 'IB<5Y';
UPDATE anm_report.dim_indicator SET indicator = 'ANC_LT_12' where indicator = 'ANC<12';
UPDATE anm_report.dim_indicator SET indicator = 'MTP_LT_12' where indicator = 'MTP<12';
UPDATE anm_report.dim_indicator SET indicator = 'MTP_GT_12' where indicator = 'MTP>12';