<%--
  Created by IntelliJ IDEA.
  User: none
  Date: 27.01.2024
  Time: 02:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="static utils.MyUtils.insertBannerToJSP" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Anime-Style Page</title>
    <style>
        /* New styles for the anime section */
        .anime-section {
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
            max-width: 800px;
        }

        .subsection {
            text-align: left;
            margin-top: 20px;
            padding-left: 20px;
            border-left: 2px solid #ff9900;
        }

        /* New styles for improved aesthetics */
        .anime-title {
            font-size: 36px;
            color: #333;
        }

        .character-subsection {
            border-left-color: #66ccff;
        }

        h2, h3 {
            font-size: 28px;
            color: #ff9900;
        }

        p.character-description {
            font-size: 20px;
            font-style: italic;
            color: #555;
        }

        /* New styles for character images */
        .character-image {
            width: 150px;
            height: 150px;
            border-radius: 5px; /* Square frame */
            margin-top: 10px;
        }

        /* New styles for the logout button */
        .logout-button {
            position: fixed;
            top: 10px;
            right: 10px;
            background-color: #ff0000; /* Red background color */
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

    </style>
</head>
<body>
<!-- New Anime Section -->

<%= insertBannerToJSP(request)%>

<!-- New logout button -->
<button class="logout-button" onclick="logout()">Logout</button>
<script>
    // JavaScript function for logout
    function logout() {
        // Perform logout logic here, such as redirecting to a logout servlet or clearing session attributes
        // For example: window.location.href = 'logoutServlet';
        window.location.href = '/logout';
    }
</script>


<div class="anime-section">
    <h2 class="anime-title">Steins;Gate Anime</h2>
    <p style="font-size: 28px;">
        "Steins;Gate" is a science fiction anime that follows the story of a young scientist named Okabe Rintarou, also known as Okabe, and his friends as they accidentally create a device that allows them to send text messages to the past, altering the course of history.
    </p>
    <div class="subsection">
        <h3>Main Plot</h3>
        <p style="font-size: 26px;">
            The main plot revolves around Okabe's discovery of the time-traveling device and the unintended consequences of manipulating the past. As the group experiments with the device, they find themselves entangled in a complex web of alternate timelines and must confront the ethical and personal dilemmas that arise.
        </p>
    </div>
    <div class="subsection character-subsection">
        <h3>Kurisu Makise</h3>
        <img src="img/kurisu.webp" alt="Kurisu Makise" class="character-image" style="width: 120px; height: auto;">
        <p class="character-description">
            Kurisu Makise is a brilliant neuroscientist and one of the central characters in the anime. Her expertise in time travel contributes to the group's efforts to understand and control their newfound ability.
        </p>
    </div>
    <div class="subsection character-subsection">
        <h3>Okabe Rintarou (Okabe)</h3>
        <img src="img/okabe.webp" alt="Okabe Rintarou" class="character-image" style="width: 120px; height: auto;">
        <p class="character-description">
            Okabe, also known as "Hououin Kyouma," is an eccentric scientist who leads the group in their experiments. He adopts a flamboyant persona and often speaks in theatrical language.
        </p>
    </div>
    <div class="subsection character-subsection">
        <h3>Itaru Hashida (Daru)</h3>
        <img src="img/daru.webp" alt="Itaru Hashida" class="character-image" style="width: 120px; height: auto;">
        <p class="character-description">
            Itaru Hashida, often referred to as "Daru," is a skilled hacker and a vital member of the group. He assists in developing and modifying the time-traveling device.
        </p>
    </div>

</div>
</body>
</html>
