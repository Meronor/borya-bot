require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Language
        q!: $regex</start>
        a: Выберите язык
        buttons:
            "Русский" -> /Russian
            "English" -> /English

    state: Russian
        a: Привет! Я чат-бот разработчиков команды "Be better".
        a: Вы согласны на обработку ваших персональных данных?
        buttons:
            "Да" -> /Special
            "Нет" -> /Russian

        state: Hello
            intent: /sys/aimylogic/ru/hello
            a: Привет привет

        state: Bye
            intent: /до свидания
            a: До встречи!

        state: NoMatch
            event: noMatch
            a: Упс, вашего вопроса нет в списке часто задаваемых вопросов...
            go!: /Russian/emailButtons

        state: KnowledgeBase
            intentGroup: /KnowledgeBase
            script:
                $faq.pushReplies();

        state: noEmail
            a: К сожалению, я ничем не можем Вам помочь. Чтобы узнать ответ на вопрос напишите нам на почту: office@park-kosa.ru

        state: emailButtons
            a: Хотите чтобы я отправил ваш вопрос на почту службы поддержки?
            buttons:
                "Да" -> /Russian/inputQuestion
                "Нет" -> /Russian/noEmail

        state: inputQuestion
            InputText: 
                prompt = Введите, пожалуйста Ваш вопрос в свободной форме
                varName = question
                then = /Russian/inputEmail
                html = 
                htmlEnabled = false
                actions = 

        state: inputEmail
            InputText: 
                prompt = Теперь введите, пожалуйста, Ваш e-mail, на который придёт ответ
                varName = mail
                then = /Russian/email
                html = 
                htmlEnabled = false
                actions = 

        state: email
            Email: 
                destination = listopad053@gmail.com
                subject = Вопрос бота
                text = Email отправителя: {{$session.mail}} 
                    {{$session.question}}
                files = []
                html = Email отправителя: {{$session.mail}} 
                    {{$session.question}}
                htmlEnabled = false
                okState = /Russian/email/success
                errorState = 

            state: success
                a: Спасибо за Ваш вопрос, письмо успешно отправлено, ожидайте ответа на почту в течение 2 часов

    state: English
        a: Salute! My name is Kosulya! I am a chatbot for the developers of the “Khalyavy ne budet” team. Ask a question you are interested in

        state: Hello
            intent: /sys/aimylogic/en/hello
            a: Hi!

        state: Bye
            intent: /bye
            a: Bye bye!

        state: NoMatch
            event: noMatch
            a: Oops, your question is not in the list of frequently asked questions...
            go!: /English/emailButtons

        state: KnowledgeBase
            intentGroup: /KnowledgeBase
            script:
                $faq.pushReplies();

        state: noEmail
            a: Unfortunately, I can't help you with anything. To find out the answer to your question, write to us by email: office@park-kosa.ru

        state: emailButtons
            a: Would you like me to send your question to the support email?
            buttons:
                "Yes" -> /English/inputQuestion
                "No" -> /English/noEmail

        state: inputQuestion
            InputText: 
                prompt = Please enter your question
                varName = question
                then = /English/inputEmail
                html = 
                htmlEnabled = false
                actions = 

        state: inputEmail
            InputText: 
                prompt = Now please enter your e-mail to which you will receive a response
                varName = mail
                then = /English/email
                html = 
                htmlEnabled = false
                actions = 

        state: email
            Email: 
                destination = listopad053@gmail.com
                subject = Вопрос бота
                text = Email отправителя: {{$session.mail}} 
                    {{$session.question}}
                files = []
                html = Email отправителя: {{$session.mail}} 
                    {{$session.question}}
                htmlEnabled = false
                okState = /English/email/success
                errorState = 

            state: success
                a: Thank you for your question, the letter was sent successfully, expect a response by email within 2 hours

    state: NewState
        intent: /sys/aimylogic/ru/hello

    state: Know
        InputText: 
            prompt = Как вас зовут? (Имя, Фамилия)
            varName = name
            html = 
            htmlEnabled = false
            then = /Age
            actions = 

    state: Age
        InputNumber: 
            prompt = Сколько вам лет?
            varName = age
            html = 
            htmlEnabled = false
            failureMessage = [""]
            failureMessageHtml = [""]
            then = /City
            minValue = 5
            maxValue = 100
            actions = 

    state: City
        InputText: 
            prompt = В каком городе вы живете?
            varName = city
            html = 
            htmlEnabled = false
            actions = 
            then = /Резюме

    state: Резюме
        InputText: 
            prompt = Отправьте ваше резюме
            varName = resume
            html = 
            htmlEnabled = false
            then = /Experiance
            actions = 

    state: Experiance
        a: Где вы работали?
        event: Match || toState = "./"

    state: dont know
        InputText: 
            prompt = Как вас зовут?(Имя, Фамилия)
            varName = name
            html = 
            htmlEnabled = false
            actions = 
            then = /Age_1

    state: Age_1
        InputText: 
            prompt = Сколько вам лет
            varName = age
            html = 
            htmlEnabled = false
            then = /City_1
            actions = 

    state: City_1
        InputText: 
            prompt = В каком городе вы живете?
            varName = city
            html = 
            htmlEnabled = false
            then = /Resume_1
            actions = 

    state: Resume_1
        InputText: 
            prompt = Отправьте ваше резюме
            varName = resume
            html = 
            htmlEnabled = false
            then = /Test
            actions = 

    state: Test
        a: Ответьте на пару вопросов, и я помогу вам с выбором специальности

        script:
            var i = 1
            while (true){
                i ++;
                $temp.res = $integration.googleSheets.readDataFromCells(
                "7f69942c-8692-4dad-aadb-825ce2e7eb1d",
                "1jG3AHyj5jYqQm22klkcWlfImk-uIaoZzw-TtDiUTKlw",
                "Лист1",
                ["A" + i.toString()]);
                
                if (typeof($temp.res) != null) {
                    $integration.googleSheets.writeDataToCells(
                        "7f69942c-8692-4dad-aadb-825ce2e7eb1d",
                        "1jG3AHyj5jYqQm22klkcWlfImk-uIaoZzw-TtDiUTKlw",
                        "Лист1",
                        [{values: [$session.name, $session.age, $session.city, $session.resume], cell:  "A" + i.toString()}]
                        );
                    break; 
                    }};
            
    state: Error
        a: Error

    state: Special
        a: Вы знаете, какая специальность вам интересна?
        buttons:
            "Да" -> /Know
            "Нет" -> /dont know