<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <%@ include file="styles.jsp" %>
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="naslov">RAF Subjects</h1>

    <form method="POST" id="vesti-form">
        <div class="form-group">
            <label for="vesti-title">Subject</label>
            <input type="text" class="form-control" id="vesti-title" placeholder="Enter vesti">

            <label for="vesti-content">Content</label>
            <textarea class="form-control" id="vesti-content"></textarea>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <br>
    <br>

    <div id="news-links">

    </div>
    <h1 id="title"></h1>
    <input id="inputField" type="text" value="neki tekst">
</div>

<script src="jquery-3.6.0.js"></script>
<script>

    // const token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbl91c2VyIiwicm9sZSI6ImFkbWluIiwiZXhwIjoxNjIxMzI4MDkwLCJpYXQiOjE2MjEyNDE2OTB9.arccNLc910xDXmLaYbIeRx8WvspbaOGUb03nMbG-hfo';
    // const decodedToken = JSON.parse(atob(token.split('.')[1]));

    fetch('/api/vestis', {
        method: 'GET'
    }).then(function (response) {
        return response.json();
    }).then(function (news) {
        for (const vesti of news) {
            addSubjectElements(vesti)
        }
    })

    function addSubjectElements(vesti) {
        const subjectLinks = document.getElementById('news-links');

        const linkWrapperDiv = document.createElement('div');

        const subjectLink = document.createElement('a');
        subjectLink.innerText = vesti.title;
        subjectLink.href = '/news/' + vesti.id;


        const removeButton = document.createElement('button');
        removeButton.innerText = 'Remove'
        removeButton.classList.add('btn', 'btn-danger')
        removeButton.style.marginLeft = '10px'
        removeButton.onclick = function (e) {
            deleteSubject(vesti.id).then(() => {
                subjectLinks.removeChild(linkWrapperDiv)
            });
        }

        linkWrapperDiv.appendChild(subjectLink);
        linkWrapperDiv.appendChild(removeButton);
        linkWrapperDiv.appendChild(document.createElement('br'));
        linkWrapperDiv.appendChild(document.createElement('br'));

        subjectLinks.appendChild(linkWrapperDiv);
    }

    function deleteSubject(id) {
        return fetch('/api/news/' + id, {
            method: 'DELETE'
        })
    }

    document.getElementById("vesti-form").addEventListener('submit', function (e) {
        e.preventDefault();

        const subjectTitleElement = document.getElementById('vesti-title');
        const contentElement = document.getElementById('vesti-content');

        const title = subjectTitleElement.value;
        const content = contentElement.value;

        fetch('/api/news', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: title,
                content: content
            })
        }).then(response => {
            return response.json();
        }).then(vesti => {
            addSubjectElements(vesti)
            subjectTitleElement.value = '';
            contentElement.value = '';
        })
    })
</script>
</body>
</html>

