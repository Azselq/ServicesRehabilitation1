package com.example.servicesrehabilitation.domain

import com.example.servicesrehabilitation.R

data class ForumPost(
    val id: Int =0,
    val userName: String = "Иван Иванов",
    val publicationTime: String = "14:00",
    val avatarResId : String = ""/*R.drawable.dog*/,
    val titleText : String = "Как ухаживать за новорожденным щенком? ",
    val contentImageResId: String = "R.drawable.little_dog",
    val contentText : String = "Забота о маленьком щенке требует особого внимания и заботы. Во-первых, необходимо обеспечить ему комфортные условия жизни – тепло, чистоту и уют. Щенку нужно предоставить место для игр и отдыха, а также доступ к еде и воде. Кроме того, важно следить за здоровьем щенка. Регулярные походы к ветеринару помогут контролировать его состояние и своевременно выявлять любые проблемы. Также необходимо обеспечить щенку правильное питание, соответствующее его возрасту и потребностям. Котенку нужна забота и внимание со стороны хозяев. Игры, ласки и общение помогут ему чувствовать себя в безопасности и любимым. Важно также обучать щенка правильному поведению и привычкам. В целом, забота об котенке требует терпения, внимания и ответственности. Вознаграждением за это будут ласковые мурчания и преданность вашего маленького друга.",
)
