	.data
_i:	.word 0
_j:	.word 0
_fila:	.word 0
_cont:	.word 0
__msg0:	.asciiz "Ingrese fila: "
__msg1:	.asciiz "*"
__msg2:	.asciiz " "
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
	li $t0, 0
	sw $t0, _i
etiq2:
	sw $s6, -4($sp)
	lw $s6, _fila
	sub $t0, $s6, 1
	lw $s6, -4($sp)
	sw $s6, -4($sp)
	lw $s6, _i
	ble $s6, $t0,etiq3
	b etiq1
	lw $s6, -4($sp)
etiq3:
	li $t0, 0
	sw $t0, _j
etiq6:
	sw $s6, -4($sp)
	lw $s6, _fila
	sub $t0, $s6, 1
	lw $s6, -4($sp)
	sw $s6, -4($sp)
	lw $s6, _j
	ble $s6, $t0,etiq7
	b etiq5
	lw $s6, -4($sp)
etiq7:
	sw $s6, -4($sp)
	lw $s6, _cont
	sw $s7, -8($sp)
	lw $s7, _i
	ble $s6, $s7,etiq10
	b etiq11
	lw $s6, -4($sp)
	lw $s7, -8($sp)
etiq10:
	li $v0, 4
	la $a0, __msg1
	syscall
	sw $s6, -4($sp)
	lw $s6, _cont
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _cont
	b etiq9
etiq11:
	li $v0, 4
	la $a0, __msg2
	syscall
etiq9:
etiq8:
	sw $s6, -4($sp)
	lw $s6, _j
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _j
	b etiq6
etiq5:
	li $t0, 0
	sw $t0, _cont
	li $v0, 4
	la $a0, __msg3
	syscall
etiq4:
	sw $s6, -4($sp)
	lw $s6, _i
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, _i
	b etiq2
etiq1:
etiq0:
	li $v0, 10
	syscall