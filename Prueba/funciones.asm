	.data
_a:	.word 0
_b:	.word 0
_c:	.word 0
_d:	.word 0
__msg0:	.asciiz "El numero "
__msg1:	.asciiz "\nElevado a la potencia de "
__msg2:	.asciiz "\nEs igual a :"
__msg3:	.asciiz "\nEl numero par: "
__msg4:	.asciiz "\n"

	.text
	.globl main
_exponent_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	move $s0, $a0
	move $s1, $a1
	move $fp, $sp
	sub $sp, $sp, 48
	move $t0, $s0
	sw $t0, 0($sp)
	li $t0, 1
	sw $t0, 4($sp)
etiq2:
	sub $t0, $s1, 1
	sw $s6, -4($sp)
	lw $s6, 4($sp)
	ble $s6, $t0,etiq3
	b etiq1
	lw $s6, -4($sp)
etiq3:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	mul $t0, $s6, $s0
	lw $s6, -4($sp)
	sw $t0, 0($sp)
etiq4:
	sw $s6, -4($sp)
	lw $s6, 4($sp)
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, 4($sp)
	b etiq2
etiq1:
	li $v0, 4
	la $a0, __msg0
	syscall
	li $v0, 1
	move $a0, $s0
	syscall
	li $v0, 4
	la $a0, __msg1
	syscall
	li $v0, 1
	move $a0, $s1
	syscall
	li $v0, 4
	la $a0, __msg2
	syscall
	li $v0, 1
	lw $a0, 0($sp)
	syscall

_exponent_INT_x_INT_END:
	move $sp, $fp
	lw $s7, -40($sp)
	lw $s6, -36($sp)
	lw $s5, -32($sp)
	lw $s4, -28($sp)
	lw $s3, -24($sp)
	lw $s2, -20($sp)
	lw $s1, -16($sp)
	lw $s0, -12($sp)
	lw $ra, -8($sp)
	sw $fp, -4($sp)
	jr $ra

_numerospares_INT_x_INT: 
	sw $fp, -4($sp)
	sw $ra, -8($sp)
	sw $s0, -12($sp)
	sw $s1, -16($sp)
	move $s0, $a0
	move $s1, $a1
	move $fp, $sp
	sub $sp, $sp, 44
	move $t0, $s0
	sw $t0, 0($sp)
etiq6:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	ble $s6, $s1,etiq7
	b etiq5
	lw $s6, -4($sp)
etiq7:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	div $t0, $s6, 2
	lw $s6, -4($sp)
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	sub $t1, $s6, 1
	lw $s6, -4($sp)
	div $t2, $t1, 2
	beq $t0, $t2,etiq9
	b etiq10
etiq10:
	li $v0, 4
	la $a0, __msg3
	syscall
	li $v0, 1
	lw $a0, 0($sp)
	syscall
etiq9:
etiq8:
	sw $s6, -4($sp)
	lw $s6, 0($sp)
	add $t0, $s6, 1
	lw $s6, -4($sp)
	sw $t0, 0($sp)
	b etiq6
etiq5:

_numerospares_INT_x_INT_END:
	move $sp, $fp
	lw $s7, -40($sp)
	lw $s6, -36($sp)
	lw $s5, -32($sp)
	lw $s4, -28($sp)
	lw $s3, -24($sp)
	lw $s2, -20($sp)
	lw $s1, -16($sp)
	lw $s0, -12($sp)
	lw $ra, -8($sp)
	sw $fp, -4($sp)
	jr $ra

main:
	move $fp, $sp
	li $t0, 2
	sw $t0, _a
	li $t0, 3
	sw $t0, _b
	li $t0, 4
	sw $t0, _c
	li $t0, 5
	sw $t0, _d
	sw $t9, -4($sp)
	sub $sp, $sp, -4
	sw $s7, -8($sp)
	li $s7, 2
	move $a0, $s7
	lw $s7, -8($sp)
	sw $s7, -8($sp)
	li $s7, 2
	move $a1, $s7
	lw $s7, -8($sp)
	jal _exponent_INT_x_INT
	add $sp, $sp, 4
	lw $t9, -4($sp)
	move $t0, $v0
	li $v0, 4
	la $a0, __msg4
	syscall
	sw $t0, -4($sp)
	sw $t9, -8($sp)
	sub $sp, $sp, -8
	sw $s7, -12($sp)
	li $s7, 4
	move $a0, $s7
	lw $s7, -12($sp)
	sw $s7, -12($sp)
	li $s7, 20
	move $a1, $s7
	lw $s7, -12($sp)
	jal _numerospares_INT_x_INT
	add $sp, $sp, 8
	lw $t9, -8($sp)
	lw $t0, -4($sp)
	move $t1, $v0
etiq0:
	li $v0, 10
	syscall