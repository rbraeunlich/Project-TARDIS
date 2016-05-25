package kr.ac.kaist.se.tardis.web.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.ac.kaist.se.tardis.web.form.CreateTaskNoteForm;

@Component
public class TaskNoteFormValidator implements Validator {

	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CreateTaskNoteForm.class.isAssignableFrom(clazz);
	}
	public static final String SHORT_COMMENT = "Comment must contain at least two characters";
	public static final String EMPTY_FORM = "There are no contents!";
	public static final String EMPTY_PROGRESS_OR_CONTRIBUTION = "Progress and Contribution are pair."; 
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		CreateTaskNoteForm taskForm = (CreateTaskNoteForm) target;
		boolean emptyComment =(taskForm.getComment()==null || taskForm.getComment().length() == 0);
		boolean emptyContribution = (taskForm.getContribution()==null || taskForm.getContribution().length() == 0);
		boolean emptyProgress = (taskForm.getProgress()==null || taskForm.getProgress().length() == 0);
		
		if(emptyComment&&emptyContribution&&emptyProgress){
			errors.rejectValue("comment", "error.comment.emptyForm", CreateTaskNoteForm.EMPTY_FORM);
		}
		
		if(!emptyComment && taskForm.getComment().length() < 2){
			errors.rejectValue("comment", "error.comment.short", CreateTaskNoteForm.SHORT_COMMENT);			
		}
		
		if(emptyProgress && !emptyContribution ){
			errors.rejectValue("progress", "error.progress.empty", CreateTaskNoteForm.EMPTY_PROGRESS_OR_CONTRIBUTION);			
		}
		if(!emptyProgress && emptyContribution ){
			errors.rejectValue("contribution", "error.contribution.empty", CreateTaskNoteForm.EMPTY_PROGRESS_OR_CONTRIBUTION);			
		}
		if(!emptyProgress){
			try {
				int a = Integer.parseInt( taskForm.getProgress());
				if (a>100 && a<0){
					errors.rejectValue("progress", "error.progress.isnotnumber", CreateTaskNoteForm.PROGRESS_MUST_NUMBER);		
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("progress", "error.progress.isnotnumber", CreateTaskNoteForm.PROGRESS_MUST_NUMBER);		
			}			
		}
		if(!emptyContribution){
			try {
				int a = Integer.parseInt( taskForm.getContribution());
				if ( a<0){
					errors.rejectValue("contribution", "error.contribution.isnotnumber", CreateTaskNoteForm.CONTRIBUTION_MUST_NUMBER);		
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("contribution", "error.contribution.isnotnumber", CreateTaskNoteForm.CONTRIBUTION_MUST_NUMBER);		
			}			
		}
	}

}
