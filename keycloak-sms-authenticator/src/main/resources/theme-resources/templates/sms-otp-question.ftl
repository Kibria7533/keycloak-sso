<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "title">
        ${msg("loginTitle",realm.name)}
    <#elseif section = "header">
        ${msg("loginTitleHtml",realm.name)}
    <#elseif section = "form">
        <form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="totp" class="${properties.kcLabelClass!}">${msg("smsotp.smsotp.label")}</label>
                </div>

                <div class="${properties.kcInputWrapperClass!}">
                    <input id="totp" name="sms_otp_answer" type="text" class="${properties.kcInputClass!}"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <div class="${properties.kcFormButtonsWrapperClass!}">
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}"
                               name="login" id="kc-login" type="submit" value="${msg("doLogIn")}"/>
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}"
                               name="resend" id="kc-resend" type="submit" value="${msg("smsotp.resend.label")} []"
                               disabled="disabled"/>
                        <script type="application/javascript">
                            (function (earliestResendAt) {
                                var resendButton = document.getElementById("kc-resend");
                                tick();

                                function tick() {
                                    var milliseconds = Math.max(0, earliestResendAt - (new Date()).getTime());
                                    if (milliseconds === 0) {
                                        resendButton.setAttribute("value", resendButton.getAttribute("value").replace(/\[\d*]$/, ""));
                                        resendButton.removeAttribute("disabled");
                                        return;
                                    }
                                    var seconds = Math.round(milliseconds / 1000);
                                    resendButton.setAttribute("value", resendButton.getAttribute("value").replace(/\[\d*]$/, "[" + seconds + "]"));
                                    setTimeout(tick, 500);
                                }
                            })(${earliestResendAt});
                        </script>
                    </div>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>
