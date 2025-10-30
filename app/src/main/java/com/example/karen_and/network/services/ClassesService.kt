package com.example.karen_and.network.services

import com.example.karen_and.models.ClassModel

class ClassesService {

    fun getMockClasses(): List<ClassModel> {
        return listOf(
            ClassModel(
                id = 1,
                title = "Introducción a Android",
                description = "Aprende los conceptos básicos de desarrollo de aplicaciones móviles con Kotlin.",
                teacher = "Agus V.",
                date = "22/10/2025",
                imageUrl = "https://example.com/android_icon.png"
            ),
            ClassModel(
                id = 2,
                title = "Fundamentos de Base de Datos",
                description = "Explora los principios de SQL y bases de datos relacionales.",
                teacher = "Martín G.",
                date = "23/10/2025",
                imageUrl = "https://example.com/db_icon.png"
            ),
            ClassModel(
                id = 3,
                title = "Arquitectura de Software",
                description = "Diseño de sistemas robustos y escalables para aplicaciones web.",
                teacher = "Carla M.",
                date = "24/10/2025",
                imageUrl = "https://example.com/software_icon.png"
            )
        )
    }
}