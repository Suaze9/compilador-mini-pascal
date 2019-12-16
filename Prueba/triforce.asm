	.data
_i:	.word 0
_j:	.word 0
_k:	.word 0
_fila:	.word 0
_cont:	.word 0
__msg0:	.asciiz "Ingrese fila: "
__msg1:	.asciiz " "
__msg2:	.asciiz "*"
__msg3:	.asciiz "\n"

	.text
	.globl main
main:
	move $fp, $sp
	li $t0, 0
	sw $t0, _cont
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 5
	syscall
	sw $v0, _fila
etiq2:
	sw $s6, -4($sp)
	lw $s6, _cont
	sw $s7, -8($sp)
	lw $s7, _fila
	blt $s6, $s7,etiq3
	b etiq1
	lw $s6, -4($sp)
	lw $s7, -8($sp)
etiq3:
	li $t0, 0
	sw $t0, _i
etiq5:
	sw $s6, -4($sp)
	lw $s6, _i
	ble $s6, 2,etiq6
	b etiq4
	lw $s6, -4($sp)
etiq6:
	li $t0, 0
	sw $t0, _j
etiq9:
	sw $s6, -4($sp)
	li $s6, 3
	sw $s7, -8($sp)
	lw $s7, _i
	sub $t0, $s6, $s7
	lw $s6, -4($sp)
	lw $s7, -8($sp)
	sw $s6, -4($sp)
	lw $s6, _j
	ble $s6, $t0,etiq10
	b etiq8
	lw $s6, -4($sp)
etiq10:
	li $v0, 4
	la $a0, __msg1
	syscall
etiq11:
	sw $s6, -4($sp)
	lw $s6, _j
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _j
	b etiq9
etiq8:
	li $t0, 0
	sw $t0, _k
etiq13:
	sw $s6, -4($sp)
	lw $s6, _i
	mul $t0, $s6, 2
	lw $s6, -4($sp)
	add $t1, $t0, 1
	sw $s6, -4($sp)
	lw $s6, _k
	ble $s6, $t1,etiq14
	b etiq12
	lw $s6, -4($sp)
etiq14:
	li $v0, 4
	la $a0, __msg2
	syscall
etiq15:
	sw $s6, -4($sp)
	lw $s6, _k
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _k
	b etiq13
etiq12:
	li $v0, 4
	la $a0, __msg3
	syscall
etiq7:
	sw $s6, -4($sp)
	lw $s6, _i
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _i
	b etiq5
etiq4:
	b etiq2
etiq1:
etiq0:
	li $v0, 10
	syscall