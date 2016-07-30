/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
      config.language = 'zh-cn';
      config.width = '495px';
      config.height = '174px';
      config.skin = 'office2003';
      config.toolbar = 'Full';
      config.filebrowserUploadUrl = 'ckeditor/uploader?Type=File';
      config.filebrowserImageUploadUrl = 'ckeditor/uploader?Type=Image';
      config.filebrowserFlashUploadUrl = 'ckeditor/uploader?Type=Flash';
      config.baseHref = '';
      config.pasteFromWordRemoveStyle = false; 
      config.pasteFromWordRemoveFontStyles = false; 
      config.disableObjectResizing = false;
    //  config.pasteFromWordKeepsStructure = false;
    //  config.pasteFromWordRemoveStyles = true;
   config.pasteFromWordPromptCleanup='default';
    //  config.pasteFromWordNumberedHeadingToList=false;
      
      config.toolbar_Full =
        [
            ['Source','-','NewPage','Preview','-','Templates'],
            ['TextColor','BGColor'],
            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
            ['Link','Unlink','Anchor'],
            ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
            ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
            ['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
            ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
            '/',
            ['Styles','Format','Font','FontSize']
        ];
};
