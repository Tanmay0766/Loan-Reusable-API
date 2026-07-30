CREATE TABLE loan_application (
                                  id BIGSERIAL PRIMARY KEY,
                                  applicant_name VARCHAR(100) NOT NULL,
                                  loan_type VARCHAR(30) NOT NULL,
                                  requested_amount NUMERIC(15,2),
                                  requested_tenure_months INT,
                                  current_stage VARCHAR(30) NOT NULL DEFAULT 'APPLICATION',
                                  created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE stage_validation_result (
                                         id BIGSERIAL PRIMARY KEY,
                                         application_id BIGINT NOT NULL REFERENCES loan_application(id),
                                         stage VARCHAR(30) NOT NULL,
                                         passed BOOLEAN NOT NULL,
                                         failure_reasons TEXT,
                                         validated_at TIMESTAMP DEFAULT now()
);