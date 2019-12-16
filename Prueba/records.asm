	.data
_p:	.word 0
_resp:	.word 0
_tip:	.word 0
_fin:	.word 0
__msg0:	.asciiz "\nIngrese Inicial del nombre: "
__msg1:	.asciiz "\nIngrese inicial del apellido: "
__msg2:	.asciiz "\nIngrese una edad: "
__msg3:	.asciiz "\nEn que unidad desea ingresar el peso? (1 = kg / 2 = lb): "
__msg4:	.asciiz "\nIngrese un peso (kg): "
__msg5:	.asciiz "\nIngrese un peso (lb): "
__msg6:	.asciiz "El valor ingresado es invalido"
__msg7:	.asciiz "\nIngrese unaa altura (cm): "
__msg8:	.asciiz "\nNombre: "
__msg9:	.asciiz ". "
__msg10:	.asciiz ".\nEdad: "
__msg11:	.asciiz "\nPeso: "
__msg12:	.asciiz "\nAltura: "
__msg13:	.asciiz "\n"
__msg14:	.asciiz "\nSu BMI es de: "
__msg15:	.asciiz "\n\nDesea salir del programa? (1 = si / 2 = no)"
__msg16:	.asciiz "\nGracias por utilizar el programa :D"

	.text
	.globl main
_bmi_INT_x_INT_x_INT_x_INT_x_persona_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $fp, $sp
	sub $sp, $sp, 20
	move $s0, $a0
	move $s1, $a1
	move $s2, $a2
	move $s3, $a3
	lw $s7, 0($fp)
	sw $s7, -4($sp)
	lw $s7, 4($fp)
	sw $s7, -8($sp)
	sw $s6, -12($sp)
	lw $s6, -4($sp)
	lw $s6, 8($s6)
	sw $s7, -16($sp)
	lw $s7, -4($sp)
	lw $s7, 8($s7)
	mul $t0, $s6, $s7
	lw $s6, -12($sp)
	lw $s7, -16($sp)
	sw $t0, 8($sp)
	sw $s6, -12($sp)
	lw $s6, 8($sp)
	div $t0, $s6, 10000
	lw $s6, -12($sp)
	sw $t0, 8($sp)
	sw $s6, -12($sp)
	lw $s6, -8($sp)
	beq $s6, 1,etiq2
	b etiq3
	lw $s6, -12($sp)
etiq2:
	sw $s6, -12($sp)
	lw $s6, -4($sp)
	lw $s6, 4($s6)
	sw $s7, -16($sp)
	lw $s7, 8($sp)
	div $t0, $s6, $s7
	lw $s6, -12($sp)
	lw $s7, -16($sp)
	sw $t0, 0($sp)
	b etiq1
etiq3:
	sw $s6, -12($sp)
	lw $s6, -4($sp)
	lw $s6, 4($s6)
	div $t0, $s6, 2
	lw $s6, -12($sp)
	sw $t0, 4($sp)
	sw $s6, -12($sp)
	lw $s6, 4($sp)
	div $t0, $s6, 10
	lw $s6, -12($sp)
	sw $s6, -12($sp)
	lw $s6, 4($sp)
	sub $t1, $s6, $t0
	lw $s6, -12($sp)
	sw $t1, 4($sp)
	sw $s6, -12($sp)
	lw $s6, 4($sp)
	sw $s7, -16($sp)
	lw $s7, 8($sp)
	div $t0, $s6, $s7
	lw $s6, -12($sp)
	lw $s7, -16($sp)
	sw $t0, 0($sp)
etiq1:
	lw $t0, 0($sp)
	move $v0, $t0
	b _bmi_INT_x_INT_x_INT_x_INT_x_persona_x_INT_END

_bmi_INT_x_INT_x_INT_x_INT_x_persona_x_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $v0, 9
	li $a0, 20
	syscall
	sw $v0, _p
etiq5:
	li $v0, 4
	la $a0, __msg0
	syscall
	lw $s6, _p
	li $v0, 12
	syscall
	sw $v0, 12($s6)
	li $v0, 4
	la $a0, __msg1
	syscall
	lw $s6, _p
	li $v0, 12
	syscall
	sw $v0, 16($s6)
	li $v0, 4
	la $a0, __msg2
	syscall
	lw $s6, _p
	li $v0, 5
	syscall
	sw $v0, 0($s6)
etiq8:
	li $v0, 4
	la $a0, __msg3
	syscall
	li $v0, 5
	syscall
	sw $v0, _tip
	sw $s6, -4($sp)
	lw $s6, _tip
	beq $s6, 1,etiq11
	b etiq12
	lw $s6, -4($sp)
etiq11:
	li $v0, 4
	la $a0, __msg4
	syscall
	lw $s6, _p
	li $v0, 5
	syscall
	sw $v0, 4($s6)
	b etiq10
etiq12:
	sw $s6, -4($sp)
	lw $s6, _tip
	beq $s6, 2,etiq14
	b etiq15
	lw $s6, -4($sp)
etiq14:
	li $v0, 4
	la $a0, __msg5
	syscall
	lw $s6, _p
	li $v0, 5
	syscall
	sw $v0, 4($s6)
	b etiq13
etiq15:
	li $v0, 4
	la $a0, __msg6
	syscall
etiq13:
etiq10:
etiq9:
	sw $s6, -4($sp)
	lw $s6, _tip
	beq $s6, 1,etiq7
	b etiq16
	lw $s6, -4($sp)
etiq16:
	sw $s6, -4($sp)
	lw $s6, _tip
	beq $s6, 2,etiq7
	b etiq8
	lw $s6, -4($sp)
etiq7:
	li $v0, 4
	la $a0, __msg7
	syscall
	lw $s6, _p
	li $v0, 5
	syscall
	sw $v0, 8($s6)
	li $v0, 4
	la $a0, __msg8
	syscall
	lw $s6, _p
	li $v0, 11
	lw $a0, 12($s6)
	syscall
	li $v0, 4
	la $a0, __msg9
	syscall
	lw $s6, _p
	li $v0, 11
	lw $a0, 16($s6)
	syscall
	li $v0, 4
	la $a0, __msg10
	syscall
	lw $s6, _p
	li $v0, 1
	lw $a0, 0($s6)
	syscall
	li $v0, 4
	la $a0, __msg11
	syscall
	lw $s6, _p
	li $v0, 1
	lw $a0, 4($s6)
	syscall
	li $v0, 4
	la $a0, __msg12
	syscall
	lw $s6, _p
	li $v0, 1
	lw $a0, 8($s6)
	syscall
	li $v0, 4
	la $a0, __msg13
	syscall
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	li $s7, 1
	move $a0, $s7
	li $s7, 2
	move $a1, $s7
	li $s7, 3
	move $a2, $s7
	li $s7, 4
	move $a3, $s7
	lw $s7, _p
	sw $s7, -8($sp)
	lw $s7, _tip
	sw $s7, -4($sp)
	sub $sp, $sp, 8
	jal _bmi_INT_x_INT_x_INT_x_INT_x_persona_x_INT
	add $sp, $sp, 8
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _resp
	li $v0, 4
	la $a0, __msg14
	syscall
	li $v0, 1
	lw $a0, _resp
	syscall
	li $v0, 4
	la $a0, __msg15
	syscall
	li $v0, 5
	syscall
	sw $v0, _fin
	li $v0, 4
	la $a0, __msg13
	syscall
etiq6:
	sw $s6, -4($sp)
	lw $s6, _fin
	beq $s6, 1,etiq4
	b etiq5
	lw $s6, -4($sp)
etiq4:
	li $v0, 4
	la $a0, __msg16
	syscall
etiq0:
	li $v0, 10
	syscall