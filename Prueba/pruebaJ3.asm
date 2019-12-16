	.data
_a:	.word 0
_b:	.word 0
_c:	.word 0
_d:	.word 0
_e:	.word 0
_f:	.word 0
_a1:	.word 0
_a2:	.word 0
_a3:	.word 0
_ho:	.word 0
__msg0:	.asciiz "ingrese a: "
__msg1:	.asciiz "ingrese b: "
__msg2:	.asciiz "ingrese c: "
__msg3:	.asciiz "ingrese d: "
__msg4:	.asciiz "ingrese e: "
__msg5:	.asciiz "ingrese f: "
__msg6:	.asciiz "ingrese a1: "
__msg7:	.asciiz "ingrese a2: "
__msg8:	.asciiz "ingrese a3: "
__msg9:	.asciiz "la suma es: "

	.text
	.globl main
_justadd_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $fp, $sp
	sub $sp, $sp, 8
	move $s0, $a0
	move $s1, $a1
	move $s2, $a2
	move $s3, $a3
	lw $s7, 0($fp)
	sw $s7, -4($sp)
	lw $s7, 4($fp)
	sw $s7, -8($sp)
	lw $s7, 8($fp)
	sw $s7, -12($sp)
	lw $s7, 12($fp)
	sw $s7, -16($sp)
	lw $s7, 16($fp)
	sw $s7, -20($sp)
	add $t0, $s0, 1
	move $s0, $t0
	add $t0, $s1, 2
	move $s1, $t0
	add $t0, $s2, 3
	move $s2, $t0
	add $t0, $s3, 4
	move $s3, $t0
	sw $s6, -24($sp)
	lw $s6, -4($sp)
	add $t0, $s6, 5
	lw $s6, -24($sp)
	sw $t0, -4($sp)
	sw $s6, -24($sp)
	lw $s6, -8($sp)
	add $t0, $s6, 6
	lw $s6, -24($sp)
	sw $t0, -8($sp)
	sw $s6, -24($sp)
	lw $s6, -12($sp)
	add $t0, $s6, 7
	lw $s6, -24($sp)
	sw $t0, -12($sp)
	sw $s6, -24($sp)
	lw $s6, -16($sp)
	add $t0, $s6, 8
	lw $s6, -24($sp)
	sw $t0, -16($sp)
	sw $s6, -24($sp)
	lw $s6, -20($sp)
	add $t0, $s6, 9
	lw $s6, -24($sp)
	sw $t0, -20($sp)
	add $t0, $s0, $s1
	add $t1, $t0, $s2
	add $t0, $t1, $s3
	sw $s7, -24($sp)
	lw $s7, -4($sp)
	add $t1, $t0, $s7
	lw $s7, -24($sp)
	sw $s7, -24($sp)
	lw $s7, -8($sp)
	add $t0, $t1, $s7
	lw $s7, -24($sp)
	sw $s7, -24($sp)
	lw $s7, -12($sp)
	add $t1, $t0, $s7
	lw $s7, -24($sp)
	sw $s7, -24($sp)
	lw $s7, -16($sp)
	add $t0, $t1, $s7
	lw $s7, -24($sp)
	sw $s7, -24($sp)
	lw $s7, -20($sp)
	add $t1, $t0, $s7
	lw $s7, -24($sp)
	move $v0, $t1
	b _justadd_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_END

_justadd_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_END:
	move $sp, $fp
	lw $ra, -8($sp)
	lw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 5
	syscall
	sw $v0, _a
	li $v0, 4
	la $a0, __msg1
	syscall
	li $v0, 5
	syscall
	sw $v0, _b
	li $v0, 4
	la $a0, __msg2
	syscall
	li $v0, 5
	syscall
	sw $v0, _c
	li $v0, 4
	la $a0, __msg3
	syscall
	li $v0, 5
	syscall
	sw $v0, _d
	li $v0, 4
	la $a0, __msg4
	syscall
	li $v0, 5
	syscall
	sw $v0, _e
	li $v0, 4
	la $a0, __msg5
	syscall
	li $v0, 5
	syscall
	sw $v0, _f
	li $v0, 4
	la $a0, __msg6
	syscall
	li $v0, 5
	syscall
	sw $v0, _a1
	li $v0, 4
	la $a0, __msg7
	syscall
	li $v0, 5
	syscall
	sw $v0, _a2
	li $v0, 4
	la $a0, __msg8
	syscall
	li $v0, 5
	syscall
	sw $v0, _a3
	sw $t9, -4($sp)
	sw $s6, -8($sp)
	sw $s7, -12($sp)
	sub $sp, $sp, 12
	lw $s7, _a
	move $a0, $s7
	lw $s7, _b
	move $a1, $s7
	lw $s7, _c
	move $a2, $s7
	lw $s7, _d
	move $a3, $s7
	lw $s7, _e
	sw $s7, -20($sp)
	lw $s7, _f
	sw $s7, -16($sp)
	lw $s7, _a1
	sw $s7, -12($sp)
	lw $s7, _a2
	sw $s7, -8($sp)
	lw $s7, _a3
	sw $s7, -4($sp)
	sub $sp, $sp, 20
	jal _justadd_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT_x_INT
	add $sp, $sp, 20
	add $sp, $sp, 12
	lw $s7, -12($sp)
	lw $s6, -8($sp)
	lw $t9, -4($sp)
	move $t0, $v0
	sw $t0, _ho
	li $v0, 4
	la $a0, __msg9
	syscall
	li $v0, 1
	lw $a0, _ho
	syscall
etiq0:
	li $v0, 10
	syscall