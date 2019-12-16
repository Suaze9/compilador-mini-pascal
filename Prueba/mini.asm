	.data
_x:	.word 0
_y:	.word 0
_z:	.word 0
_i:	.word 0
_res:	.word 0
_boo:	.word 0
_lean:	.word 0
_c:	.word 0
__msg0:	.asciiz "\nx: "
__msg1:	.asciiz "\ny: "
__msg2:	.asciiz "\nz: "
__msg3:	.asciiz "\nIngrese el valor de res (un numero cuyo cuadrado sea 25): "
__msg4:	.asciiz "ingerso el numero: "
__msg5:	.asciiz "\ncuyo cuadrado es: "
__msg6:	.asciiz "\nel cuadrado de res no es 25...\n"
__msg7:	.asciiz "\nBien!!!!"

	.text
	.globl main
main:
	move $fp, $sp
	li $t0, 3
	sw $t0, _x
	li $t0, 4
	sw $t0, _y
	li $t0, 5
	sw $t0, _z
	sw $s6, -4($sp)
	li $s6, 9
	add $t0, $s6, 3
	lw $s6, -4($sp)
	sub $t1, $t0, 5
	sw $s7, -4($sp)
	lw $s7, _z
	mul $t0, $t1, $s7
	lw $s7, -4($sp)
	sw $t0, _res
etiq2:
	sw $s6, -4($sp)
	lw $s6, _x
	bgt $s6, 10,etiq1
	b etiq3
	lw $s6, -4($sp)
etiq3:
	sw $s6, -4($sp)
	lw $s6, _x
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _x
	b etiq2
etiq1:
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 1
	lw $a0, _x
	syscall
	li $v0, 4
	la $a0, __msg1
	syscall
	li $v0, 1
	lw $a0, _y
	syscall
	li $v0, 4
	la $a0, __msg2
	syscall
	li $v0, 1
	lw $a0, _z
	syscall
	b etiq4
etiq6:
	sw $s6, -4($sp)
	lw $s6, _x
	sw $s7, -8($sp)
	lw $s7, _y
	blt $s6, $s7,etiq4
	b etiq5
	lw $s6, -4($sp)
	lw $s7, -8($sp)
etiq4:
	li $t0, 1
	b etiq7
etiq5:
	li $t0, 0
etiq7:
	sw $t0, _boo
etiq9:
	li $v0, 4
	la $a0, __msg3
	syscall
	li $v0, 5
	syscall
	sw $v0, _res
	li $v0, 4
	la $a0, __msg4
	syscall
	li $v0, 1
	lw $a0, _res
	syscall
	sw $s6, -4($sp)
	lw $s6, _res
	sw $s7, -8($sp)
	lw $s7, _res
	mul $t0, $s6, $s7
	lw $s6, -4($sp)
	lw $s7, -8($sp)
	sw $t0, _i
	li $v0, 4
	la $a0, __msg5
	syscall
	li $v0, 1
	lw $a0, _i
	syscall
	sw $s6, -4($sp)
	lw $s6, _i
	beq $s6, 25,etiq11
	b etiq12
	lw $s6, -4($sp)
etiq12:
	li $v0, 4
	la $a0, __msg6
	syscall
etiq11:
etiq10:
	sw $s6, -4($sp)
	lw $s6, _i
	beq $s6, 25,etiq8
	b etiq9
	lw $s6, -4($sp)
etiq8:
	li $v0, 4
	la $a0, __msg7
	syscall
etiq0:
	li $v0, 10
	syscall